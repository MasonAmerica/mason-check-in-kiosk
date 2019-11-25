@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION")

import kotlin.js.Promise

typealias CredentialHelperType = String

external interface Callbacks {
    val signInSuccessWithAuthResult: ((authResult: Any, redirectUrl: String? /* = null */) -> Boolean)?
        get() = definedExternally
    val signInFailure: ((error: firebaseui.auth.AuthUIError) -> Promise<Unit>)?
        get() = definedExternally
    val uiShown: (() -> Unit)?
        get() = definedExternally
}

external interface SignInOption {
    var provider: String
}

external interface SamlSignInOption : SignInOption {
    var providerName: String?
        get() = definedExternally
        set(value) = definedExternally
    var buttonColor: String
    var iconUrl: String
}

external interface FederatedSignInOption : SignInOption {
    var authMethod: String?
        get() = definedExternally
        set(value) = definedExternally
    var clientId: String?
        get() = definedExternally
        set(value) = definedExternally
    var scopes: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var customParameters: Any?
        get() = definedExternally
        set(value) = definedExternally
}

external interface OAuthSignInOption : SignInOption {
    var providerName: String?
        get() = definedExternally
        set(value) = definedExternally
    var buttonColor: String
    var iconUrl: String
    var scopes: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var customParameters: Any?
        get() = definedExternally
        set(value) = definedExternally
    var loginHintKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface OidcSignInOption : SignInOption {
    var providerName: String?
        get() = definedExternally
        set(value) = definedExternally
    var buttonColor: String
    var iconUrl: String
    var customParameters: Any?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$0` {
    var bundleId: String
}

external interface `T$1` {
    var packageName: String
    var installApp: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var minimumVersion: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ActionCodeSettings {
    var url: String
    var handleCodeInApp: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var iOS: `T$0`?
        get() = definedExternally
        set(value) = definedExternally
    var android: `T$1`?
        get() = definedExternally
        set(value) = definedExternally
    var dynamicLinkDomain: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface EmailSignInOption : SignInOption {
    var forceSameDevice: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var requireDisplayName: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var signInMethod: String?
        get() = definedExternally
        set(value) = definedExternally
    val emailLinkSignIn: (() -> ActionCodeSettings)?
        get() = definedExternally
}

external interface `T$2` {
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
    var size: String?
        get() = definedExternally
        set(value) = definedExternally
    var badge: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface PhoneSignInOption : SignInOption {
    var recaptchaParameters: `T$2`?
        get() = definedExternally
        set(value) = definedExternally
    var defaultCountry: String?
        get() = definedExternally
        set(value) = definedExternally
    var defaultNationalNumber: String?
        get() = definedExternally
        set(value) = definedExternally
    var loginHint: String?
        get() = definedExternally
        set(value) = definedExternally
    var whitelistedCountries: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var blacklistedCountries: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
}
