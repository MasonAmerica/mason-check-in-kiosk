package com.bymason.kiosk.checkin.functions

import express.Request
import express.Response
import firebase.firestore.SetOptions
import firebase.functions.admin
import firebase.functions.functions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import superagent.superagent
import kotlin.js.json

fun handleSlackAuth(req: Request<Any?>, res: Response<Any?>) {
    val error = req.query["error"]
    if (error != null) {
        console.log(error)
        res.status(400).send(error)
        return
    }

    GlobalScope.launch { fetchSlackAccessToken(req, res) }
}

private suspend fun fetchSlackAccessToken(req: Request<Any?>, res: Response<Any?>) {
    val result = superagent.get("https://slack.com/api/oauth.v2.access")
            .query(json(
                    "code" to req.query["code"],
                    "client_id" to functions.config().slack.client_id,
                    "client_secret" to functions.config().slack.client_secret
            )).await()

    if (result.body["ok"].unsafeCast<Boolean>()) {
        admin.firestore()
                .collection("credentials")
                .doc(req.query["state"].unsafeCast<String>())
                .set(json("slack" to result.body["access_token"]), SetOptions.merge)
                .await()
        res.redirect("/")
    } else {
        console.log(result.body)
        res.status(401).send(result.body)
    }
}
