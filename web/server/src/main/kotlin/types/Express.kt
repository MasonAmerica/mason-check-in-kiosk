@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION")

package express

import kotlin.js.Json

external interface NextFunction {
    @nativeInvoke
    operator fun invoke(err: Any? = definedExternally)
}

external interface Request<T> {
    fun get(name: String /* "set-cookie" */): Array<String>?
    fun get(name: String): String?
    fun header(name: String /* "set-cookie" */): Array<String>?
    fun header(name: String): String?
    fun accepts(): Array<String>
    fun accepts(type: String): dynamic /* String | false */
    fun accepts(type: Array<String>): dynamic /* String | false */
    fun accepts(vararg type: String): dynamic /* String | false */
    fun acceptsCharsets(): Array<String>
    fun acceptsCharsets(charset: String): dynamic /* String | false */
    fun acceptsCharsets(charset: Array<String>): dynamic /* String | false */
    fun acceptsCharsets(vararg charset: String): dynamic /* String | false */
    fun acceptsEncodings(): Array<String>
    fun acceptsEncodings(encoding: String): dynamic /* String | false */
    fun acceptsEncodings(encoding: Array<String>): dynamic /* String | false */
    fun acceptsEncodings(vararg encoding: String): dynamic /* String | false */
    fun acceptsLanguages(): Array<String>
    fun acceptsLanguages(lang: String): dynamic /* String | false */
    fun acceptsLanguages(lang: Array<String>): dynamic /* String | false */
    fun acceptsLanguages(vararg lang: String): dynamic /* String | false */
    var accepted: Array<MediaType>
    fun param(name: String, defaultValue: Any? = definedExternally): String
    fun `is`(type: String): dynamic /* String | false */
    var protocol: String
    var secure: Boolean
    var ip: String
    var ips: Array<String>
    var subdomains: Array<String>
    var path: String
    var hostname: String
    var host: String
    var fresh: Boolean
    var stale: Boolean
    var xhr: Boolean
    var body: T
    var cookies: Any
    var method: String
    var params: Json
    var query: Json
    var route: Any
    var signedCookies: Any
    var originalUrl: String
    var url: String
    var baseUrl: String
    var res: Response<*>?
        get() = definedExternally
        set(value) = definedExternally
    var next: NextFunction?
        get() = definedExternally
        set(value) = definedExternally

    fun accepts(type: String): dynamic /* String | false */
    fun acceptsCharsets(charset: String): dynamic /* String | false */
    fun acceptsEncodings(encoding: String): dynamic /* String | false */
    fun acceptsLanguages(lang: String): dynamic /* String | false */
}

external interface MediaType {
    var value: String
    var quality: Number
    var type: String
    var subtype: String
}

typealias Send<ResBody, T> = (body: ResBody? /* = null */) -> T

external interface Response<ResBody> {
    fun status(code: Number): Response<ResBody> /* this */
    fun sendStatus(code: Number): Response<ResBody> /* this */
    fun links(links: Any): Response<ResBody> /* this */
    var send: Send<ResBody, Response<ResBody> /* this */>
    var json: Send<ResBody, Response<ResBody> /* this */>
    var jsonp: Send<ResBody, Response<ResBody> /* this */>
    fun sendfile(path: String)
    fun sendfile(path: String, options: Any)
    fun contentType(type: String): Response<ResBody> /* this */
    fun type(type: String): Response<ResBody> /* this */
    fun format(obj: Any): Response<ResBody> /* this */
    fun attachment(filename: String? = definedExternally): Response<ResBody> /* this */
    fun set(field: Any): Response<ResBody> /* this */
    fun set(field: String, value: String? = definedExternally): Response<ResBody> /* this */
    fun set(field: String, value: Array<String>? = definedExternally): Response<ResBody> /* this */
    fun header(field: Any): Response<ResBody> /* this */
    fun header(field: String, value: String? = definedExternally): Response<ResBody> /* this */
    fun header(field: String, value: Array<String>? = definedExternally): Response<ResBody> /* this */
    var headersSent: Boolean
    fun get(field: String): String
    fun clearCookie(name: String, options: Any? = definedExternally): Response<ResBody> /* this */
    fun cookie(name: String, `val`: Any): Response<ResBody> /* this */
    fun location(url: String): Response<ResBody> /* this */
    fun redirect(url: String)
    fun redirect(status: Number, url: String)
    fun redirect(url: String, status: Number)
    fun render(view: String, options: Any? = definedExternally, callback: ((err: Error, html: String) -> Unit)? = definedExternally)
    fun render(view: String, callback: ((err: Error, html: String) -> Unit)? = definedExternally)
    var locals: Any
    var charset: String
    fun vary(field: String): Response<ResBody> /* this */
    fun append(field: String, value: Array<String>? = definedExternally): Response<ResBody> /* this */
    fun append(field: String, value: String? = definedExternally): Response<ResBody> /* this */
    var req: Request<*>?
        get() = definedExternally
        set(value) = definedExternally

    fun set(field: String): Response<ResBody> /* this */
    fun header(field: String): Response<ResBody> /* this */
    fun append(field: String): Response<ResBody> /* this */
}

typealias RequestParamHandler = (req: Request<*>, res: Response<Any>, next: NextFunction, value: Any, name: String) -> Any
