package com.bymason.kiosk.checkin

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
                            "signInMethod" to EmailAuthProvider.EMAIL_LINK_SIGN_IN_METHOD,
                            "forceSameDevice" to false
                    ))
            ))
        } else {
            onSignedIn(user!!)
        }
    })
}

fun onSignedIn(user: User) {
    document.getElementById("add-to-slack")?.setAttribute(
            "href", "https://slack.com/oauth/v2/authorize?" +
            "client_id=2392918492.847431680672&" +
            "scope=users:read,users:read.email,chat:write&" +
            "state=${user.uid}")
}
