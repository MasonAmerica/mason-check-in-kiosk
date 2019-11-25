@file:JsQualifier("firebase.app")
@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION")

package firebase.app

import kotlin.js.Promise

external interface App {
    fun auth(): firebase.auth.Auth
    fun database(url: String? = definedExternally): firebase.database.Database
    fun delete(): Promise<Any>
    var name: String
    var options: Any
    fun storage(url: String? = definedExternally): firebase.storage.Storage
    fun firestore(): firebase.firestore.Firestore
    fun functions(region: String? = definedExternally): firebase.functions.Functions
    fun performance(): firebase.performance.Performance
    fun remoteConfig(): firebase.remoteConfig.RemoteConfig
    fun analytics(): firebase.analytics.Analytics
}
