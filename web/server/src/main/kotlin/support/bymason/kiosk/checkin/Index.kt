package support.bymason.kiosk.checkin

import firebase.functions.admin
import firebase.functions.functions
import support.bymason.kiosk.checkin.functions.cleanupCompletedSessions
import support.bymason.kiosk.checkin.functions.cleanupIncompleteSessions
import support.bymason.kiosk.checkin.functions.handleDocusignAuth
import support.bymason.kiosk.checkin.functions.handleGSuiteAuth
import support.bymason.kiosk.checkin.functions.handleSlackAuth
import support.bymason.kiosk.checkin.functions.processClientRequest
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

    exports.clientApi = functions.runWith(json("memory" to "2GB")).https
            .onCall<Json> { data, context -> processClientRequest(data, context) }

    exports.cleanupIncompleteSessions = functions.runWith(json("timeoutSeconds" to 540))
            .pubsub.topic("daily-tick")
            .onPublish { _, _ -> cleanupIncompleteSessions() }
    exports.cleanupCompletedSessions = functions.runWith(json("timeoutSeconds" to 540))
            .pubsub.topic("daily-tick")
            .onPublish { _, _ -> cleanupCompletedSessions() }
}
