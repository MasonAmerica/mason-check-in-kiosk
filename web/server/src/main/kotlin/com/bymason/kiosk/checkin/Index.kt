package com.bymason.kiosk.checkin

import com.bymason.kiosk.checkin.functions.findEmployees
import com.bymason.kiosk.checkin.functions.handleGSuiteAuth
import com.bymason.kiosk.checkin.functions.handleSlackAuth
import firebase.functions.admin
import firebase.functions.functions

external val exports: dynamic

fun main() {
    admin.initializeApp()

    exports.gsuiteAuthHandler = functions.https
            .onRequest<Any?> { req, res -> handleGSuiteAuth(req, res) }
    exports.slackAuthHandler = functions.https
            .onRequest<Any?> { req, res -> handleSlackAuth(req, res) }

    exports.findEmployees = functions.https
            .onCall<String> { data, context -> findEmployees(data, context) }
}
