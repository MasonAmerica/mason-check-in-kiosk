@file:JsQualifier("firebase.storage")
@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION")

package firebase.storage

import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Uint8Array
import org.w3c.files.Blob
import kotlin.js.Promise

external interface FullMetadata : firebase.storage.UploadMetadata {
    var bucket: String
    var downloadURLs: Array<String>
    var fullPath: String
    var generation: String
    var metageneration: String
    var name: String
    var size: Number
    var timeCreated: String
    var updated: String
}

external interface Reference {
    var bucket: String
    fun child(path: String): firebase.storage.Reference
    fun delete(): Promise<Any>
    var fullPath: String
    fun getDownloadURL(): Promise<Any>
    fun getMetadata(): Promise<Any>
    var name: String
    var parent: firebase.storage.Reference?
        get() = definedExternally
        set(value) = definedExternally

    fun put(data: Blob, metadata: firebase.storage.UploadMetadata? = definedExternally): firebase.storage.UploadTask
    fun put(data: Uint8Array, metadata: firebase.storage.UploadMetadata? = definedExternally): firebase.storage.UploadTask
    fun put(data: ArrayBuffer, metadata: firebase.storage.UploadMetadata? = definedExternally): firebase.storage.UploadTask
    fun putString(data: String, format: firebase.storage.StringFormat? = definedExternally, metadata: firebase.storage.UploadMetadata? = definedExternally): firebase.storage.UploadTask
    var root: firebase.storage.Reference
    var storage: firebase.storage.Storage
    override fun toString(): String
    fun updateMetadata(metadata: firebase.storage.SettableMetadata): Promise<Any>
    fun listAll(): Promise<ListResult>
    fun list(options: ListOptions? = definedExternally): Promise<ListResult>
}

external interface ListResult {
    var prefixes: Array<Reference>
    var items: Array<Reference>
    var nextPageToken: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ListOptions {
    var maxResults: Number?
        get() = definedExternally
        set(value) = definedExternally
    var pageToken: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$26` {
    @nativeGetter
    operator fun get(key: String): String?
    @nativeSetter
    operator fun set(key: String, value: String)
}

external interface SettableMetadata {
    var cacheControl: String?
        get() = definedExternally
        set(value) = definedExternally
    var contentDisposition: String?
        get() = definedExternally
        set(value) = definedExternally
    var contentEncoding: String?
        get() = definedExternally
        set(value) = definedExternally
    var contentLanguage: String?
        get() = definedExternally
        set(value) = definedExternally
    var contentType: String?
        get() = definedExternally
        set(value) = definedExternally
    var customMetadata: dynamic /* `T$26` | Nothing? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface Storage {
    var app: firebase.app.App
    var maxOperationRetryTime: Number
    var maxUploadRetryTime: Number
    fun ref(path: String? = definedExternally): firebase.storage.Reference
    fun refFromURL(url: String): firebase.storage.Reference
    fun setMaxOperationRetryTime(time: Number): Any
    fun setMaxUploadRetryTime(time: Number): Any
}

external object StringFormat {
    var BASE64: StringFormat
    var BASE64URL: StringFormat
    var DATA_URL: StringFormat
    var RAW: StringFormat
}

external object TaskEvent {
    var STATE_CHANGED: TaskEvent
}

external object TaskState {
    var CANCELED: TaskState
    var ERROR: TaskState
    var PAUSED: TaskState
    var RUNNING: TaskState
    var SUCCESS: TaskState
}

external interface UploadMetadata : firebase.storage.SettableMetadata {
    var md5Hash: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface UploadTask {
    fun cancel(): Boolean
    fun catch(onRejected: (a: Error) -> Any): Promise<Any>
    fun on(event: firebase.storage.TaskEvent, nextOrObserver: firebase.Observer<UploadTaskSnapshot, Error>? = definedExternally, error: ((a: Error) -> Any)? = definedExternally, complete: firebase.Unsubscribe? = definedExternally): Function<*>
    fun on(event: firebase.storage.TaskEvent, nextOrObserver: Nothing? = definedExternally, error: ((a: Error) -> Any)? = definedExternally, complete: firebase.Unsubscribe? = definedExternally): Function<*>
    fun on(event: firebase.storage.TaskEvent, nextOrObserver: ((a: UploadTaskSnapshot) -> Any)? = definedExternally, error: ((a: Error) -> Any)? = definedExternally, complete: firebase.Unsubscribe? = definedExternally): Function<*>
    fun pause(): Boolean
    fun resume(): Boolean
    var snapshot: firebase.storage.UploadTaskSnapshot
    fun then(onFulfilled: ((a: firebase.storage.UploadTaskSnapshot) -> Any)? = definedExternally, onRejected: ((a: Error) -> Any)? = definedExternally): Promise<Any>
    fun on(event: firebase.storage.TaskEvent): Function<*>
}

external interface UploadTaskSnapshot {
    var bytesTransferred: Number
    var downloadURL: String?
        get() = definedExternally
        set(value) = definedExternally
    var metadata: firebase.storage.FullMetadata
    var ref: firebase.storage.Reference
    var state: firebase.storage.TaskState
    var task: firebase.storage.UploadTask
    var totalBytes: Number
}
