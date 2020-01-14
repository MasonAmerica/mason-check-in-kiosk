package support.bymason.kiosk.checkin

import firebase.functions.admin
import firebase.functions.functions
import support.bymason.kiosk.checkin.functions.cleanupIncompleteSessions
import support.bymason.kiosk.checkin.functions.findHosts
import support.bymason.kiosk.checkin.functions.generateNdaLink
import support.bymason.kiosk.checkin.functions.getCompanyMetadata
import support.bymason.kiosk.checkin.functions.getGuestFields
import support.bymason.kiosk.checkin.functions.handleDocusignAuth
import support.bymason.kiosk.checkin.functions.handleGSuiteAuth
import support.bymason.kiosk.checkin.functions.handleSlackAuth
import support.bymason.kiosk.checkin.functions.updateSession
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

    exports.getCompanyMetadata = functions.https
            .onCall<Any?> { _, context -> getCompanyMetadata(context) }
    exports.getGuestFields = functions.https
            .onCall<Any?> { _, context -> getGuestFields(context) }
    exports.updateSession = functions.runWith(json("memory" to "1GB")).https
            .onCall<Json> { data, context -> updateSession(data, context) }
    exports.findHosts = functions.runWith(json("memory" to "512MB")).https
            .onCall<Any?> { data, context -> findHosts(data, context) }
    exports.generateNdaLink = functions.runWith(json("memory" to "512MB")).https
            .onCall<Any?> { data, context -> generateNdaLink(data, context) }

    exports.cleanupIncompleteSessions = functions.runWith(json("timeoutSeconds" to 300))
            .pubsub.schedule("0 3 * * *")
            .onRun { cleanupIncompleteSessions() }
}
