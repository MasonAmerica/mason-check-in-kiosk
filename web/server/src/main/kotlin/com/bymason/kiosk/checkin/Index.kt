package com.bymason.kiosk.checkin

import com.bymason.kiosk.checkin.functions.findEmployees
import com.bymason.kiosk.checkin.functions.generateNdaLink
import com.bymason.kiosk.checkin.functions.getGuestFields
import com.bymason.kiosk.checkin.functions.handleDocusignAuth
import com.bymason.kiosk.checkin.functions.handleGSuiteAuth
import com.bymason.kiosk.checkin.functions.handleSlackAuth
import com.bymason.kiosk.checkin.functions.updateSession
import firebase.functions.admin
import firebase.functions.functions
import kotlin.js.Json
import kotlin.js.json

external val exports: dynamic

fun main() {
    admin.initializeApp()

    exports.gsuiteAuthHandler = functions.https
            .onRequest<Any?> { req, res -> handleGSuiteAuth(req, res) }
    exports.docusignAuthHandler = functions.https
            .onRequest<Any?> { req, res -> handleDocusignAuth(req, res) }
    exports.slackAuthHandler = functions.https
            .onRequest<Any?> { req, res -> handleSlackAuth(req, res) }

    exports.getGuestFields = functions.https
            .onCall<Any?> { _, context -> getGuestFields(context) }
    exports.updateSession = functions.runWith(json("memory" to "1GB")).https
            .onCall<Json> { data, context -> updateSession(data, context) }
    exports.findEmployees = functions.runWith(json("memory" to "512MB")).https
            .onCall<Any?> { data, context -> findEmployees(data, context) }
    exports.generateNdaLink = functions.runWith(json("memory" to "512MB")).https
            .onCall<Any?> { data, context -> generateNdaLink(data, context) }
}
