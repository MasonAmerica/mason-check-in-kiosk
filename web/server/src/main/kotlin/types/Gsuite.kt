@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION")

package google

import admin_directory_v1.Admin
import superagent.Response
import kotlin.js.Json
import kotlin.js.Promise

val google = js("require('googleapis').google").unsafeCast<GoogleApis>()

open external class GoogleApis(options: GlobalOptions? = definedExternally) {
    open var _discovery: Any
    open var auth: AuthPlus
    open var _options: GlobalOptions
    open fun options(options: Json? = definedExternally)
    open var addAPIs: Any
    open fun discover(url: String): Promise<Unit>
    open fun discover(url: String, callback: (err: Error? /* = null */) -> Unit)
    open var discoverAsync: Any
    open fun admin(options: Any?): Admin
}

open external class AuthPlus {
    open var JWT: Any
    open var Compute: Any
    open var OAuth2: Any
    open var GoogleAuth: Any
    open var _cachedAuth: Any
    open fun getClient(options: Json? = definedExternally): Promise<dynamic /* Compute | JWT | UserRefreshClient */>
    open fun getProjectId(): Promise<String>
}

open external class OAuth2(options: Any?)

open external class AuthClient {
    open var credentials: Credentials
    open fun setCredentials(credentials: Credentials)
    fun on(event: String /* 'tokens' */, listener: (tokens: Credentials) -> Unit): AuthClient /* this */
}

external interface Credentials {
    var refresh_token: String?
        get() = definedExternally
        set(value) = definedExternally
    var expiry_date: Number?
        get() = definedExternally
        set(value) = definedExternally
    var access_token: String?
        get() = definedExternally
        set(value) = definedExternally
    var token_type: String?
        get() = definedExternally
        set(value) = definedExternally
    var id_token: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CredentialRequest {
    var refresh_token: String?
        get() = definedExternally
        set(value) = definedExternally
    var access_token: String?
        get() = definedExternally
        set(value) = definedExternally
    var token_type: String?
        get() = definedExternally
        set(value) = definedExternally
    var expires_in: Number?
        get() = definedExternally
        set(value) = definedExternally
    var id_token: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface JWTInput {
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
    var client_email: String?
        get() = definedExternally
        set(value) = definedExternally
    var private_key: String?
        get() = definedExternally
        set(value) = definedExternally
    var private_key_id: String?
        get() = definedExternally
        set(value) = definedExternally
    var project_id: String?
        get() = definedExternally
        set(value) = definedExternally
    var client_id: String?
        get() = definedExternally
        set(value) = definedExternally
    var client_secret: String?
        get() = definedExternally
        set(value) = definedExternally
    var refresh_token: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CredentialBody {
    var client_email: String?
        get() = definedExternally
        set(value) = definedExternally
    var private_key: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Certificates

@Suppress("NOTHING_TO_INLINE")
inline operator fun Certificates.get(index: String): dynamic /* String | JwkCertificate */ = asDynamic()[index]

@Suppress("NOTHING_TO_INLINE")
inline operator fun Certificates.set(index: String, value: String) {
    asDynamic()[index] = value
}

external interface Headers

@Suppress("NOTHING_TO_INLINE")
inline operator fun Headers.get(index: String): String? = asDynamic()[index]

@Suppress("NOTHING_TO_INLINE")
inline operator fun Headers.set(index: String, value: String) {
    asDynamic()[index] = value
}

external enum class CodeChallengeMethod {
    Plain /* = "plain" */,
    S256 /* = "S256" */
}

external enum class CertificateFormat {
    PEM /* = "PEM" */,
    JWK /* = "JWK" */
}

external interface GetTokenOptions {
    var code: String
    var codeVerifier: String?
        get() = definedExternally
        set(value) = definedExternally
    var client_id: String?
        get() = definedExternally
        set(value) = definedExternally
    var redirect_uri: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface TokenInfo {
    var aud: String
    var user_id: String?
        get() = definedExternally
        set(value) = definedExternally
    var scopes: Array<String>
    var expiry_date: Number
    var sub: String?
        get() = definedExternally
        set(value) = definedExternally
    var azp: String?
        get() = definedExternally
        set(value) = definedExternally
    var access_type: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface GenerateAuthUrlOpts {
    var access_type: String?
        get() = definedExternally
        set(value) = definedExternally
    var hd: String?
        get() = definedExternally
        set(value) = definedExternally
    var response_type: String?
        get() = definedExternally
        set(value) = definedExternally
    var client_id: String?
        get() = definedExternally
        set(value) = definedExternally
    var redirect_uri: String?
        get() = definedExternally
        set(value) = definedExternally
    var scope: dynamic /* Array<String> | String */
        get() = definedExternally
        set(value) = definedExternally
    var state: String?
        get() = definedExternally
        set(value) = definedExternally
    var include_granted_scopes: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var login_hint: String?
        get() = definedExternally
        set(value) = definedExternally
    var prompt: String?
        get() = definedExternally
        set(value) = definedExternally
    var code_challenge_method: CodeChallengeMethod?
        get() = definedExternally
        set(value) = definedExternally
    var code_challenge: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface GetTokenResponse {
    var tokens: Credentials
}

external interface GetAccessTokenResponse {
    var token: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface RefreshAccessTokenResponse {
    var credentials: Credentials
}

external interface RequestMetadataResponse {
    var headers: Headers
}

external interface FederatedSignonCertsResponse {
    var certs: Certificates
    var format: CertificateFormat
}

external interface RevokeCredentialsResult {
    var success: Boolean
}

external interface VerifyIdTokenOptions {
    var idToken: String
    var audience: dynamic /* String | Array<String> */
        get() = definedExternally
        set(value) = definedExternally
    var maxExpiry: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface OAuth2ClientOptions : RefreshOptions {
    var clientId: String?
        get() = definedExternally
        set(value) = definedExternally
    var clientSecret: String?
        get() = definedExternally
        set(value) = definedExternally
    var redirectUri: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface RefreshOptions {
    var eagerRefreshThresholdMillis: Number?
        get() = definedExternally
        set(value) = definedExternally
    var forceRefreshOnFailure: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$0` {
    var codeVerifier: String
    var codeChallenge: String
}

open external class OAuth2Client(options: OAuth2ClientOptions? = definedExternally) : AuthClient {
    constructor(clientId: String?, clientSecret: String?, redirectUri: String?)

    open var redirectUri: Any
    open var certificateCache: Any
    open var certificateExpiry: Any
    open var certificateCacheFormat: Any
    open var refreshTokenPromises: Map<String, Promise<GetTokenResponse>>
    open var _clientId: String
    open var _clientSecret: String
    open var apiKey: String
    open var projectId: String
    open var eagerRefreshThresholdMillis: Number
    open var forceRefreshOnFailure: Boolean
    open fun generateAuthUrl(opts: GenerateAuthUrlOpts? = definedExternally): String
    open fun generateCodeVerifier()
    open fun generateCodeVerifierAsync(): Promise<`T$0`>
    open fun getToken(code: String): Promise<GetTokenResponse>
    open fun getToken(options: GetTokenOptions): Promise<GetTokenResponse>
    open var getTokenAsync: Any
    open fun refreshToken(refreshToken: String? = definedExternally): Promise<GetTokenResponse>
    open fun refreshTokenNoCache(refreshToken: String? = definedExternally): Promise<GetTokenResponse>
    open fun refreshAccessToken(): Promise<RefreshAccessTokenResponse>
    open var refreshAccessTokenAsync: Any
    open fun getAccessToken(): Promise<GetAccessTokenResponse>
    open var getAccessTokenAsync: Any
    open fun getRequestMetadata(url: String? = definedExternally): Promise<RequestMetadataResponse>
    open fun getRequestHeaders(url: String? = definedExternally): Promise<Headers>
    open fun getRequestMetadataAsync(url: String? = definedExternally): Promise<RequestMetadataResponse>
    open fun revokeToken(token: String): Promise<RevokeCredentialsResult>
    open fun revokeToken(token: String, callback: BodyResponseCallback<RevokeCredentialsResult>)
    open fun revokeCredentials(): Promise<RevokeCredentialsResult>
    open fun revokeCredentials(callback: BodyResponseCallback<RevokeCredentialsResult>)
    open var revokeCredentialsAsync: Any
    open var verifyIdTokenAsync: Any
    open fun getTokenInfo(accessToken: String): Promise<TokenInfo>
    open fun getFederatedSignonCerts(): Promise<FederatedSignonCertsResponse>
    open fun getFederatedSignonCertsAsync(): Promise<FederatedSignonCertsResponse>
    open fun verifySignedJwtWithCerts()
    open fun isTokenExpiring(): Boolean

    companion object {
        var GOOGLE_TOKEN_INFO_URL: Any
        var GOOGLE_OAUTH2_AUTH_BASE_URL_: Any
        var GOOGLE_OAUTH2_TOKEN_URL_: Any
        var GOOGLE_OAUTH2_REVOKE_URL_: Any
        var GOOGLE_OAUTH2_FEDERATED_SIGNON_PEM_CERTS_URL_: Any
        var GOOGLE_OAUTH2_FEDERATED_SIGNON_JWK_CERTS_URL_: Any
        var CLOCK_SKEW_SECS_: Any
        var MAX_TOKEN_LIFETIME_SECS_: Any
        var ISSUERS_: Any
        fun getRevokeTokenUrl(token: String): String
    }
}

external interface APIRequestParams<T> {
    var options: MethodOptions
    var params: T
    var requiredParams: Array<String>
    var pathParams: Array<String>
    var context: APIRequestContext
    var mediaUrl: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface GoogleConfigurable {
    var _options: GlobalOptions
}

external interface APIRequestContext {
    var google: GoogleConfigurable?
        get() = definedExternally
        set(value) = definedExternally
    var _options: GlobalOptions
}

external interface GlobalOptions : MethodOptions {
    var auth: dynamic /* OAuth2Client | String */
        get() = definedExternally
        set(value) = definedExternally
}

external interface MethodOptions {
    var rootUrl: String?
        get() = definedExternally
        set(value) = definedExternally
    var userAgentDirectives: Array<UserAgentDirective>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface UserAgentDirective {
    var product: String
    var version: String
    var comment: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ServiceOptions : GlobalOptions {
    var version: String?
        get() = definedExternally
        set(value) = definedExternally
}

typealias BodyResponseCallback<T> = (err: Error?, res: Response?) -> Unit
