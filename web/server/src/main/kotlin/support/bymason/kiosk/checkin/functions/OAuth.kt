package support.bymason.kiosk.checkin.functions

import express.Request
import express.Response
import firebase.firestore.SetOptions
import firebase.functions.admin
import firebase.functions.functions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asPromise
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import superagent.superagent
import support.bymason.kiosk.checkin.utils.buildGoogleAuthClient
import kotlin.js.Json
import kotlin.js.Promise
import kotlin.js.json

fun handleGSuiteAuth(req: Request<Any?>, res: Response<Any?>): Promise<*>? {
    if (!checkForError(req, res)) return null
    return GlobalScope.async { fetchGSuiteAccessToken(req, res) }.asPromise()
}

fun handleDocusignAuth(req: Request<Any?>, res: Response<Any?>): Promise<*>? {
    if (!checkForError(req, res)) return null
    return GlobalScope.async { fetchDocusignAccessToken(req, res) }.asPromise()
}

fun handleSlackAuth(req: Request<Any?>, res: Response<Any?>): Promise<*>? {
    if (!checkForError(req, res)) return null
    return GlobalScope.async { fetchSlackAccessToken(req, res) }.asPromise()
}

private fun checkForError(req: Request<Any?>, res: Response<Any?>): Boolean {
    val error = req.query["error"]
    if (error != null) {
        console.warn(error)
        res.status(400).send(error)
        return false
    }
    return true
}

private suspend fun fetchGSuiteAccessToken(req: Request<Any?>, res: Response<Any?>) {
    val client = buildGoogleAuthClient()
    val creds = try {
        client.getToken(req.query["code"].unsafeCast<String>()).await().tokens
    } catch (e: Throwable) {
        val error = e.asDynamic().response?.data ?: e.toString()
        console.warn(error)
        res.status(401).send(error)
        return
    }

    admin.firestore()
            .collection(req.query["state"].unsafeCast<String>())
            .doc("config")
            .collection("credentials")
            .doc("gsuite")
            .set(creds.unsafeCast<Json>(), SetOptions.merge)
            .await()
    res.redirect("/")
}

private suspend fun fetchDocusignAccessToken(req: Request<Any?>, res: Response<Any?>) {
    val credsResult = try {
        superagent.post("https://account-d.docusign.com/oauth/token")
                .set(json("Authorization" to "Basic ${functions.config().docusign.client_hash}"))
                .query(json(
                        "grant_type" to "authorization_code",
                        "code" to req.query["code"]
                )).await().body
    } catch (e: Throwable) {
        val error = e.asDynamic().response?.text ?: e.toString()
        console.warn(error)
        res.status(401).send(error)
        return
    }

    val accountResult = try {
        superagent.get("https://account-d.docusign.com/oauth/userinfo")
                .set(json("Authorization" to "Bearer ${credsResult["access_token"]}"))
                .await().body
    } catch (e: Throwable) {
        val error = e.asDynamic().response?.text ?: e.toString()
        console.warn(error)
        res.status(401).send(error)
        return
    }

    val ref = admin.firestore()
            .collection(req.query["state"].unsafeCast<String>())
            .doc("config")
            .collection("credentials")
            .doc("docusign")
    ref.set(accountResult, SetOptions.merge).await()
    ref.set(credsResult, SetOptions.merge).await()
    res.redirect("/")
}

private suspend fun fetchSlackAccessToken(req: Request<Any?>, res: Response<Any?>) {
    val result = superagent.get("https://slack.com/api/oauth.v2.access")
            .query(json(
                    "code" to req.query["code"],
                    "client_id" to functions.config().slack.client_id,
                    "client_secret" to functions.config().slack.client_secret
            )).await().body

    if (result["ok"].unsafeCast<Boolean>()) {
        admin.firestore()
                .collection(req.query["state"].unsafeCast<String>())
                .doc("config")
                .collection("credentials")
                .doc("slack")
                .set(result, SetOptions.merge)
                .await()
        res.redirect("/")
    } else {
        console.warn(result)
        res.status(401).send(result)
    }
}
