@file:JsModule("firebase/app")
@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION")

package firebase

import kotlin.js.Promise

external interface FirebaseError {
    var code: String
    var message: String
    var name: String
    var stack: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Observer<T, E> {
    var next: NextFn<T>
    var error: ErrorFn<E>
    var complete: CompleteFn
}

external var SDK_VERSION: String

external interface `T$0` {
    var displayName: String?
        get() = definedExternally
        set(value) = definedExternally
    var photoURL: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface User : firebase.UserInfo {
    fun delete(): Promise<Unit>
    var emailVerified: Boolean
    fun getIdTokenResult(forceRefresh: Boolean? = definedExternally): Promise<firebase.auth.IdTokenResult>
    fun getIdToken(forceRefresh: Boolean? = definedExternally): Promise<String>
    var isAnonymous: Boolean
    fun linkAndRetrieveDataWithCredential(credential: firebase.auth.AuthCredential): Promise<firebase.auth.UserCredential>
    fun linkWithCredential(credential: firebase.auth.AuthCredential): Promise<firebase.auth.UserCredential>
    fun linkWithPhoneNumber(phoneNumber: String, applicationVerifier: firebase.auth.ApplicationVerifier): Promise<firebase.auth.ConfirmationResult>
    fun linkWithPopup(provider: firebase.auth.AuthProvider): Promise<firebase.auth.UserCredential>
    fun linkWithRedirect(provider: firebase.auth.AuthProvider): Promise<Unit>
    var metadata: firebase.auth.UserMetadata
    var providerData: Array<firebase.UserInfo?>
    fun reauthenticateAndRetrieveDataWithCredential(credential: firebase.auth.AuthCredential): Promise<firebase.auth.UserCredential>
    fun reauthenticateWithCredential(credential: firebase.auth.AuthCredential): Promise<firebase.auth.UserCredential>
    fun reauthenticateWithPhoneNumber(phoneNumber: String, applicationVerifier: firebase.auth.ApplicationVerifier): Promise<firebase.auth.ConfirmationResult>
    fun reauthenticateWithPopup(provider: firebase.auth.AuthProvider): Promise<firebase.auth.UserCredential>
    fun reauthenticateWithRedirect(provider: firebase.auth.AuthProvider): Promise<Unit>
    var refreshToken: String
    fun reload(): Promise<Unit>
    fun sendEmailVerification(actionCodeSettings: firebase.auth.ActionCodeSettings? = definedExternally): Promise<Unit>
    var tenantId: String?
        get() = definedExternally
        set(value) = definedExternally

    fun toJSON(): Any
    fun unlink(providerId: String): Promise<firebase.User>
    fun updateEmail(newEmail: String): Promise<Unit>
    fun updatePassword(newPassword: String): Promise<Unit>
    fun updatePhoneNumber(phoneCredential: firebase.auth.AuthCredential): Promise<Unit>
    fun updateProfile(profile: `T$0`): Promise<Unit>
}

external interface UserInfo {
    var displayName: String?
        get() = definedExternally
        set(value) = definedExternally
    var email: String?
        get() = definedExternally
        set(value) = definedExternally
    var phoneNumber: String?
        get() = definedExternally
        set(value) = definedExternally
    var photoURL: String?
        get() = definedExternally
        set(value) = definedExternally
    var providerId: String
    var uid: String
}

external fun app(name: String? = definedExternally): firebase.app.App

external var apps: Array<firebase.app.App>

external fun auth(app: firebase.app.App? = definedExternally): firebase.auth.Auth

external fun database(app: firebase.app.App? = definedExternally): firebase.database.Database

external fun initializeApp(options: Any, name: String? = definedExternally): firebase.app.App

external fun storage(app: firebase.app.App? = definedExternally): firebase.storage.Storage

external fun firestore(app: firebase.app.App? = definedExternally): firebase.firestore.Firestore

external fun functions(app: firebase.app.App? = definedExternally): firebase.functions.Functions

external fun performance(app: firebase.app.App? = definedExternally): firebase.performance.Performance

external fun remoteConfig(app: firebase.app.App? = definedExternally): firebase.remoteConfig.RemoteConfig

external fun analytics(app: firebase.app.App? = definedExternally): firebase.analytics.Analytics
