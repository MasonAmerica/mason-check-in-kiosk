package support.bymason.kiosk.checkin

import firebase.User
import firebase.auth.EmailAuthProvider
import firebaseui.auth.AuthUI
import kotlin.browser.document
import kotlin.js.json

fun main() {
    val firebase = firebase.initializeApp(json(
            "apiKey" to "AIzaSyBLJ0na8K6VWfSkV9UbQiol6dFSAt591tc",
            "authDomain" to "mason-check-in-kiosk.firebaseapp.com",
            "databaseURL" to "https://mason-check-in-kiosk.firebaseio.com",
            "projectId" to "mason-check-in-kiosk",
            "storageBucket" to "mason-check-in-kiosk.appspot.com",
            "messagingSenderId" to "153912363552",
            "appId" to "1:153912363552:web:7864bc28141edddc120a45",
            "measurementId" to "G-PEFSMWL3LF"
    ))
    val auth = firebase.auth()
    val firebaseUi = AuthUI(auth)

    auth.onAuthStateChanged(nextOrObserver = { user ->
        val signedOut = document.getElementById("signed-out-container")
        val signedIn = document.getElementById("signed-in-container")
        val signInRequired = user == null || firebaseUi.isPendingRedirect()
        val signOutVisibility = if (signInRequired) "block" else "none"
        val signInVisibility = if (signInRequired) "none" else "block"

        signedOut?.setAttribute("style", "display: $signOutVisibility")
        signedIn?.setAttribute("style", "display: $signInVisibility")

        if (signInRequired) {
            firebaseUi.start("#signed-out-container", json(
                    "signInOptions" to arrayOf(json(
                            "provider" to EmailAuthProvider.PROVIDER_ID,
                            "requireDisplayName" to false
                    ))
            ))
        } else {
            onSignedIn(user!!)
        }
    })
}

fun onSignedIn(user: User) {
    document.getElementById("add-to-google")?.setAttribute(
            "href", "https://accounts.google.com/o/oauth2/v2/auth?" +
            "client_id=153912363552-dla8r4rf4uekcs094i0up3kp0p5vqcp7.apps.googleusercontent.com&" +
            "scope=https://www.googleapis.com/auth/admin.directory.user.readonly&" +
            "access_type=offline&" +
            "response_type=code&" +
            "redirect_uri=https://mason-check-in-kiosk.firebaseapp.com/redirect/gsuite/auth&" +
            "state=${user.uid}")
    document.getElementById("add-to-docusign")?.setAttribute(
            "href", "https://account-d.docusign.com/oauth/auth?" +
            "client_id=b9bf0128-0695-4ad2-8ec8-ec9d1ba3bf76&" +
            "scope=signature&" +
            "response_type=code&" +
            "redirect_uri=https://mason-check-in-kiosk.firebaseapp.com/redirect/docusign/auth&" +
            "state=${user.uid}")
    document.getElementById("add-to-slack")?.setAttribute(
            "href", "https://slack.com/oauth/v2/authorize?" +
            "client_id=2392918492.847431680672&" +
            "scope=users:read,users:read.email,chat:write&" +
            "redirect_uri=https://mason-check-in-kiosk.firebaseapp.com/redirect/slack/auth&" +
            "state=${user.uid}")
}
