@file:Suppress(
        "INTERFACE_WITH_SUPERCLASS",
        "OVERRIDING_FINAL_MEMBER",
        "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
        "CONFLICTING_OVERLOADS",
        "unused"
)

package firebase.https

import express.Request
import express.Response
import firebase.functions.AuthContext
import firebase.functions.functions
import kotlin.js.Promise

@Suppress("FunctionName", "UNUSED_PARAMETER", "UNUSED_VARIABLE") // Fake class
fun HttpsError(code: String, message: String? = null, details: Any? = null): Nothing {
    val functions = functions
    js("throw new functions.https.HttpsError(code, message, details)")
    throw Exception() // Never going to get called
}

external class Https {
    fun <T> onCall(handler: (data: T, context: CallableContext) -> Promise<*>?): dynamic = definedExternally
    fun <T> onRequest(handler: (Request<Any?>, Response<Any?>) -> Promise<*>?): dynamic = definedExternally
}

external interface CallableContext {
    val auth: AuthContext? get() = definedExternally
    val instanceIdToken: String? get() = definedExternally
}
