package support.bymason.kiosk.checkin

import firebase.User
import firebase.auth.EmailAuthProvider
import firebase.initializeApp
import firebaseui.auth.AuthUI
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLInputElement
import kotlin.browser.document
import kotlin.js.Json
import kotlin.js.json

val firebase by lazy {
    initializeApp(json(
            "apiKey" to "AIzaSyBLJ0na8K6VWfSkV9UbQiol6dFSAt591tc",
            "authDomain" to "mason-check-in-kiosk.firebaseapp.com",
            "databaseURL" to "https://mason-check-in-kiosk.firebaseio.com",
            "projectId" to "mason-check-in-kiosk",
            "storageBucket" to "mason-check-in-kiosk.appspot.com",
            "messagingSenderId" to "153912363552",
            "appId" to "1:153912363552:web:7864bc28141edddc120a45",
            "measurementId" to "G-PEFSMWL3LF"
    ))
}

fun main() {
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
    js("require('firebase/firestore')")

    initConnectionButtons(user)
    initConfigurationFields(user)
}

private fun initConnectionButtons(user: User) {
    document.getElementById("add-to-google")?.setAttribute(
            "href", "https://accounts.google.com/o/oauth2/v2/auth?" +
            "client_id=153912363552-dla8r4rf4uekcs094i0up3kp0p5vqcp7.apps.googleusercontent.com&" +
            "scope=https://www.googleapis.com/auth/admin.directory.user.readonly&" +
            "access_type=offline&" +
            "response_type=code&" +
            "redirect_uri=https://mason-check-in-kiosk.firebaseapp.com/redirect/gsuite/auth&" +
            "state=${user.uid}")
    document.getElementById("add-to-slack")?.setAttribute(
            "href", "https://slack.com/oauth/v2/authorize?" +
            "client_id=2392918492.847431680672&" +
            "scope=users:read,users:read.email,chat:write&" +
            "redirect_uri=https://mason-check-in-kiosk.firebaseapp.com/redirect/slack/auth&" +
            "state=${user.uid}")
}

fun initConfigurationFields(user: User) {
    val companyName = document.getElementById("company-name") as HTMLInputElement
    val companyLogo = document.getElementById("company-logo") as HTMLInputElement
    val esignaturesTemplateId =
            document.getElementById("esignatures-template-id") as HTMLInputElement
    val esignaturesApiToken = document.getElementById("esignatures-api-token") as HTMLInputElement
    val save = document.getElementById("save") as HTMLButtonElement

    val companyRef = firebase.firestore()
            .collection(user.uid)
            .doc("config")
            .collection("metadata")
            .doc("company")
    val documentsRef = firebase.firestore()
            .collection(user.uid)
            .doc("config")
            .collection("metadata")
            .doc("documents")
    val esignaturesCredsRef = firebase.firestore()
            .collection(user.uid)
            .doc("config")
            .collection("credentials")
            .doc("esignatures")

    save.onclick = {
        companyRef.set(json(
                "name" to companyName.value,
                "logo" to companyLogo.value
        ), json("merge" to true))
        documentsRef.set(json(
                "nda" to "esignatures",
                "esignatures" to json(
                        "individual-nda" to esignaturesTemplateId.value,
                        "corporate-nda" to esignaturesTemplateId.value
                )
        ), json("merge" to true))
        esignaturesCredsRef.set(json(
                "secret" to esignaturesApiToken.value
        ), json("merge" to true))
    }

    GlobalScope.launch {
        launch {
            val company = companyRef.get().await()

            companyName.value = company.get("name") as? String ?: ""
            companyLogo.value = company.get("logo") as? String ?: ""
        }

        launch {
            val documents = documentsRef.get().await()

            val esignatures = documents.get("esignatures") as Json
            esignaturesTemplateId.value = esignatures["individual-nda"] as? String ?: ""
        }

        launch {
            val esignaturesCreds = esignaturesCredsRef.get().await()

            esignaturesApiToken.value = esignaturesCreds.get("secret") as? String ?: ""
        }
    }
}
