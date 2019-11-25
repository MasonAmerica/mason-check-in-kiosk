@file:JsQualifier("auth")
@file:JsModule("firebaseui")
@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION")

package firebaseui.auth

import Callbacks
import CredentialHelperType
import org.w3c.dom.Element
import kotlin.js.Promise

external interface Config {
    var acUiConfig: Any?
        get() = definedExternally
        set(value) = definedExternally
    var autoUpgradeAnonymousUsers: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var callbacks: Callbacks?
        get() = definedExternally
        set(value) = definedExternally
    var credentialHelper: CredentialHelperType?
        get() = definedExternally
        set(value) = definedExternally
    var popupMode: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var queryParameterForSignInSuccessUrl: String?
        get() = definedExternally
        set(value) = definedExternally
    var queryParameterForWidgetMode: String?
        get() = definedExternally
        set(value) = definedExternally
    var signInFlow: String?
        get() = definedExternally
        set(value) = definedExternally
    var signInOptions: Array<dynamic /* String | FederatedSignInOption | EmailSignInOption | PhoneSignInOption | SamlSignInOption | OAuthSignInOption | OidcSignInOption */>?
        get() = definedExternally
        set(value) = definedExternally
    var signInSuccessUrl: String?
        get() = definedExternally
        set(value) = definedExternally
    var siteName: String?
        get() = definedExternally
        set(value) = definedExternally
    var tosUrl: dynamic /* () -> Unit | String */
        get() = definedExternally
        set(value) = definedExternally
    var privacyPolicyUrl: dynamic /* () -> Unit | String */
        get() = definedExternally
        set(value) = definedExternally
    var widgetUrl: String?
        get() = definedExternally
        set(value) = definedExternally
}

open external class AuthUI(auth: Any, appId: String? = definedExternally) {
    open fun disableAutoSignIn()
    open fun start(element: String, config: Any)
    open fun start(element: Element, config: firebaseui.auth.Config)
    open fun setConfig(config: firebaseui.auth.Config)
    open fun signIn()
    open fun reset()
    open fun delete(): Promise<Unit>
    open fun isPendingRedirect(): Boolean

    companion object {
        fun getInstance(appId: String? = definedExternally): AuthUI?
    }
}

open external class AuthUIError {
    open var code: String
    open var message: String
    open var credential: Any?
    open fun toJSON(): Any?
}

open external class CredentialHelper {
    companion object {
        var ACCOUNT_CHOOSER_COM: CredentialHelperType
        var GOOGLE_YOLO: CredentialHelperType
        var NONE: CredentialHelperType
    }
}

open external class AnonymousAuthProvider {
    companion object {
        var PROVIDER_ID: String
    }
}
