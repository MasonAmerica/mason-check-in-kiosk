package support.bymason.kiosk.checkin

import firebase.functions.admin
import firebase.functions.functions
import support.bymason.kiosk.checkin.functions.cleanupCompletedSessions
import support.bymason.kiosk.checkin.functions.cleanupIncompleteSessions
import support.bymason.kiosk.checkin.functions.handleDocusignAuth
import support.bymason.kiosk.checkin.functions.handleGSuiteAuth
import support.bymason.kiosk.checkin.functions.handleSlackAuth
import support.bymason.kiosk.checkin.functions.initUser
import support.bymason.kiosk.checkin.functions.processClientRequest
import support.bymason.kiosk.checkin.functions.sendNotifications
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

    exports.initUser = functions.auth.user().onCreate { user -> initUser(user) }

    exports.clientApi = functions.runWith(json("memory" to "2GB")).https
            .onCall<Json> { data, context -> processClientRequest(data, context) }

    exports.sendNotifications = functions.firestore.document("notifications/{id}")
            .onCreate { snapshot, _ -> sendNotifications(snapshot) }

    val cleanupRuntime = json("memory" to "1GB", "timeoutSeconds" to 540)
    exports.cleanupIncompleteSessions = functions.runWith(cleanupRuntime)
            .pubsub.topic("daily-tick")
            .onPublish { _, _ -> cleanupIncompleteSessions() }
    exports.cleanupCompletedSessions = functions.runWith(cleanupRuntime)
            .pubsub.topic("daily-tick")
            .onPublish { _, _ -> cleanupCompletedSessions() }
}
