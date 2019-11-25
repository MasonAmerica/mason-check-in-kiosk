@file:JsQualifier("auth")
@file:JsModule("firebase/app")
@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION")

package firebase.auth

import kotlin.js.Json
import kotlin.js.Promise

open external class ActionCodeURL {
    open var apiKey: String
    open var code: String
    open var continueUrl: String?
    open var languageCode: String?
    open var tenantId: String?

    companion object {
        fun parseLink(link: String): firebase.auth.ActionCodeURL?
    }
}

external interface `T$6` {
    var email: String?
        get() = definedExternally
        set(value) = definedExternally
    var fromEmail: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$7` {
    var installApp: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var minimumVersion: String?
        get() = definedExternally
        set(value) = definedExternally
    var packageName: String
}

external interface `T$8` {
    var bundleId: String
}

external interface ActionCodeSettings {
    var android: `T$7`?
        get() = definedExternally
        set(value) = definedExternally
    var handleCodeInApp: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var iOS: `T$8`?
        get() = definedExternally
        set(value) = definedExternally
    var url: String
    var dynamicLinkDomain: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface AdditionalUserInfo {
    var isNewUser: Boolean
    var profile: Any?
        get() = definedExternally
        set(value) = definedExternally
    var providerId: String
    var username: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ApplicationVerifier {
    var type: String
    fun verify(): Promise<String>
}

external interface AuthSettings {
    var appVerificationDisabledForTesting: Boolean
}

external interface Auth {
    var app: firebase.app.App
    fun applyActionCode(code: String): Promise<Unit>
    fun confirmPasswordReset(code: String, newPassword: String): Promise<Unit>
    fun createUserWithEmailAndPassword(email: String, password: String): Promise<firebase.auth.UserCredential>
    var currentUser: firebase.User?
        get() = definedExternally
        set(value) = definedExternally

    fun fetchSignInMethodsForEmail(email: String): Promise<Array<String>>
    fun isSignInWithEmailLink(emailLink: String): Boolean
    fun getRedirectResult(): Promise<firebase.auth.UserCredential>
    var languageCode: String?
        get() = definedExternally
        set(value) = definedExternally
    var settings: firebase.auth.AuthSettings
    fun onAuthStateChanged(nextOrObserver: firebase.Observer<Any, Error>, error: ((a: firebase.auth.Error) -> Any)? = definedExternally, completed: firebase.Unsubscribe? = definedExternally): firebase.Unsubscribe
    fun onAuthStateChanged(nextOrObserver: (a: firebase.User?) -> Unit, error: ((a: firebase.auth.Error) -> Any)? = definedExternally, completed: firebase.Unsubscribe? = definedExternally): firebase.Unsubscribe
    fun onIdTokenChanged(nextOrObserver: firebase.Observer<Any, Error>, error: ((a: firebase.auth.Error) -> Any)? = definedExternally, completed: firebase.Unsubscribe? = definedExternally): firebase.Unsubscribe
    fun onIdTokenChanged(nextOrObserver: (a: firebase.User?) -> Any, error: ((a: firebase.auth.Error) -> Any)? = definedExternally, completed: firebase.Unsubscribe? = definedExternally): firebase.Unsubscribe
    fun sendSignInLinkToEmail(email: String, actionCodeSettings: firebase.auth.ActionCodeSettings): Promise<Unit>
    fun sendPasswordResetEmail(email: String, actionCodeSettings: firebase.auth.ActionCodeSettings? = definedExternally): Promise<Unit>
    fun signInAndRetrieveDataWithCredential(credential: firebase.auth.AuthCredential): Promise<firebase.auth.UserCredential>
    fun signInAnonymously(): Promise<firebase.auth.UserCredential>
    fun signInWithCredential(credential: firebase.auth.AuthCredential): Promise<firebase.auth.UserCredential>
    fun signInWithCustomToken(token: String): Promise<firebase.auth.UserCredential>
    fun signInWithEmailAndPassword(email: String, password: String): Promise<firebase.auth.UserCredential>
    fun signInWithPhoneNumber(phoneNumber: String, applicationVerifier: firebase.auth.ApplicationVerifier): Promise<firebase.auth.ConfirmationResult>
    fun signInWithEmailLink(email: String, emailLink: String? = definedExternally): Promise<firebase.auth.UserCredential>
    fun signInWithPopup(provider: firebase.auth.AuthProvider): Promise<firebase.auth.UserCredential>
    fun signInWithRedirect(provider: firebase.auth.AuthProvider): Promise<Unit>
    fun signOut(): Promise<Unit>
    var tenantId: String?
        get() = definedExternally
        set(value) = definedExternally

    fun updateCurrentUser(user: firebase.User?): Promise<Unit>
    fun useDeviceLanguage()
    fun verifyPasswordResetCode(code: String): Promise<String>
}

open external class AuthCredential {
    open var providerId: String
    open var signInMethod: String
    open fun toJSON(): Any

    companion object {
        fun fromJSON(json: Any): AuthCredential?
        fun fromJSON(json: String): AuthCredential?
    }
}

open external class OAuthCredential : AuthCredential {
    open var idToken: String
    open var accessToken: String
    open var secret: String
}

external interface AuthProvider

external interface ConfirmationResult {
    fun confirm(verificationCode: String): Promise<firebase.auth.UserCredential>
    var verificationId: String
}

open external class EmailAuthProvider : EmailAuthProvider_Instance {
    companion object {
        var PROVIDER_ID: String
        var EMAIL_PASSWORD_SIGN_IN_METHOD: String
        var EMAIL_LINK_SIGN_IN_METHOD: String
        fun credential(email: String, password: String): firebase.auth.AuthCredential
        fun credentialWithLink(email: String, emailLink: String): firebase.auth.AuthCredential
    }
}

open external class EmailAuthProvider_Instance : firebase.auth.AuthProvider {
    open var providerId: String
}

external interface Error {
    var code: String
    var message: String
}

external interface AuthError : firebase.auth.Error {
    var credential: firebase.auth.AuthCredential?
        get() = definedExternally
        set(value) = definedExternally
    var email: String?
        get() = definedExternally
        set(value) = definedExternally
    var phoneNumber: String?
        get() = definedExternally
        set(value) = definedExternally
    var tenantId: String?
        get() = definedExternally
        set(value) = definedExternally
}

open external class FacebookAuthProvider : FacebookAuthProvider_Instance {
    companion object {
        var PROVIDER_ID: String
        var FACEBOOK_SIGN_IN_METHOD: String
        fun credential(token: String): firebase.auth.OAuthCredential
    }
}

open external class FacebookAuthProvider_Instance : firebase.auth.AuthProvider {
    open fun addScope(scope: String): firebase.auth.AuthProvider
    open var providerId: String
    open fun setCustomParameters(customOAuthParameters: Any): firebase.auth.AuthProvider
}

open external class GithubAuthProvider : GithubAuthProvider_Instance {
    companion object {
        var PROVIDER_ID: String
        var GITHUB_SIGN_IN_METHOD: String
        fun credential(token: String): firebase.auth.OAuthCredential
    }
}

open external class GithubAuthProvider_Instance : firebase.auth.AuthProvider {
    open fun addScope(scope: String): firebase.auth.AuthProvider
    open var providerId: String
    open fun setCustomParameters(customOAuthParameters: Any): firebase.auth.AuthProvider
}

open external class GoogleAuthProvider : GoogleAuthProvider_Instance {
    companion object {
        var PROVIDER_ID: String
        var GOOGLE_SIGN_IN_METHOD: String
        fun credential(idToken: String? = definedExternally, accessToken: String? = definedExternally): firebase.auth.OAuthCredential
    }
}

open external class GoogleAuthProvider_Instance : firebase.auth.AuthProvider {
    open fun addScope(scope: String): firebase.auth.AuthProvider
    open var providerId: String
    open fun setCustomParameters(customOAuthParameters: Any): firebase.auth.AuthProvider
}

open external class OAuthProvider(providerId: String) : firebase.auth.AuthProvider {
    open var providerId: String
    open fun addScope(scope: String): firebase.auth.AuthProvider
    open fun credential(optionsOrIdToken: firebase.auth.OAuthCredentialOptions, accessToken: String? = definedExternally): firebase.auth.OAuthCredential
    open fun credential(optionsOrIdToken: String, accessToken: String? = definedExternally): firebase.auth.OAuthCredential
    open fun credential(optionsOrIdToken: Nothing?, accessToken: String? = definedExternally): firebase.auth.OAuthCredential
    open fun setCustomParameters(customOAuthParameters: Any): firebase.auth.AuthProvider
}

open external class SAMLAuthProvider : firebase.auth.AuthProvider {
    open var providerId: String
}

external interface IdTokenResult {
    var token: String
    var expirationTime: String
    var authTime: String
    var issuedAtTime: String
    var signInProvider: String?
        get() = definedExternally
        set(value) = definedExternally
    var claims: Json
}

external interface OAuthCredentialOptions {
    var idToken: String?
        get() = definedExternally
        set(value) = definedExternally
    var accessToken: String?
        get() = definedExternally
        set(value) = definedExternally
    var rawNonce: String?
        get() = definedExternally
        set(value) = definedExternally
}

open external class PhoneAuthProvider : PhoneAuthProvider_Instance {
    companion object {
        var PROVIDER_ID: String
        var PHONE_SIGN_IN_METHOD: String
        fun credential(verificationId: String, verificationCode: String): firebase.auth.AuthCredential
    }
}

open external class PhoneAuthProvider_Instance(auth: firebase.auth.Auth? = definedExternally) : firebase.auth.AuthProvider {
    open var providerId: String
    open fun verifyPhoneNumber(phoneNumber: String, applicationVerifier: firebase.auth.ApplicationVerifier): Promise<String>
}

open external class RecaptchaVerifier : RecaptchaVerifier_Instance

open external class RecaptchaVerifier_Instance : firebase.auth.ApplicationVerifier {
    constructor(container: Any, parameters: Any?, app: firebase.app.App?)
    constructor(container: String, parameters: Any?, app: firebase.app.App?)

    open fun clear()
    open fun render(): Promise<Number>
    override var type: String
    override fun verify(): Promise<String>
}

open external class TwitterAuthProvider : TwitterAuthProvider_Instance {
    companion object {
        var PROVIDER_ID: String
        var TWITTER_SIGN_IN_METHOD: String
        fun credential(token: String, secret: String): firebase.auth.OAuthCredential
    }
}

open external class TwitterAuthProvider_Instance : firebase.auth.AuthProvider {
    open var providerId: String
    open fun setCustomParameters(customOAuthParameters: Any): firebase.auth.AuthProvider
}

external interface UserCredential {
    var additionalUserInfo: firebase.auth.AdditionalUserInfo?
        get() = definedExternally
        set(value) = definedExternally
    var credential: firebase.auth.AuthCredential?
        get() = definedExternally
        set(value) = definedExternally
    var operationType: String?
        get() = definedExternally
        set(value) = definedExternally
    var user: firebase.User?
        get() = definedExternally
        set(value) = definedExternally
}

external interface UserMetadata {
    var creationTime: String?
        get() = definedExternally
        set(value) = definedExternally
    var lastSignInTime: String?
        get() = definedExternally
        set(value) = definedExternally
}
