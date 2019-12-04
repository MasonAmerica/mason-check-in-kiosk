package com.bymason.kiosk.checkin

import com.bymason.kiosk.checkin.functions.findEmployees
import com.bymason.kiosk.checkin.functions.finishCheckIn
import com.bymason.kiosk.checkin.functions.handleDocusignAuth
import com.bymason.kiosk.checkin.functions.handleGSuiteAuth
import com.bymason.kiosk.checkin.functions.handleSlackAuth
import firebase.functions.admin
import firebase.functions.functions
import kotlin.js.Json

external val exports: dynamic

fun main() {
    admin.initializeApp()

    exports.gsuiteAuthHandler = functions.https
            .onRequest<Any?> { req, res -> handleGSuiteAuth(req, res) }
    exports.docusignAuthHandler = functions.https
            .onRequest<Any?> { req, res -> handleDocusignAuth(req, res) }
    exports.slackAuthHandler = functions.https
            .onRequest<Any?> { req, res -> handleSlackAuth(req, res) }

    exports.findEmployees = functions.https
            .onCall<String> { data, context -> findEmployees(data, context) }
    exports.finishCheckIn = functions.https
            .onCall<Json> { data, context -> finishCheckIn(data, context) }
}
