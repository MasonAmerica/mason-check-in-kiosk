package com.bymason.kiosk.checkin

import com.bymason.kiosk.checkin.functions.handleSlackAuth
import firebase.functions.admin
import firebase.functions.functions

external val exports: dynamic

fun main() {
    admin.initializeApp()

    exports.slackAuthHandler = functions.https
            .onRequest<Any?> { req, res -> handleSlackAuth(req, res) }
}
