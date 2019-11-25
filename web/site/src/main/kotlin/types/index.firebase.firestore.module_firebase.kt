@file:JsQualifier("firebase.firestore")
@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION")

package firebase.firestore

import org.khronos.webgl.Uint8Array
import kotlin.js.Date
import kotlin.js.Promise

external interface DocumentData {
    @nativeGetter
    operator fun get(field: String): Any?
    @nativeSetter
    operator fun set(field: String, value: Any)
}

external interface UpdateData {
    @nativeGetter
    operator fun get(fieldPath: String): Any?
    @nativeSetter
    operator fun set(fieldPath: String, value: Any)
}

external var CACHE_SIZE_UNLIMITED: Number

external interface Settings {
    var host: String?
        get() = definedExternally
        set(value) = definedExternally
    var ssl: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var timestampsInSnapshots: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var cacheSizeBytes: Number?
        get() = definedExternally
        set(value) = definedExternally
    var experimentalForceLongPolling: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface PersistenceSettings {
    var synchronizeTabs: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var experimentalTabSynchronization: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external fun setLogLevel(logLevel: String /* 'debug' */)

external interface `T$27` {
    var next: ((value: Unit) -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
    var error: ((error: Error) -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
    var complete: (() -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$28` {
    var delete: () -> Promise<Unit>
}

open external class Firestore {
    open fun settings(settings: Settings)
    open fun enablePersistence(settings: PersistenceSettings? = definedExternally): Promise<Unit>
    open fun collection(collectionPath: String): CollectionReference
    open fun doc(documentPath: String): DocumentReference
    open fun collectionGroup(collectionId: String): Query
    open fun <T> runTransaction(updateFunction: (transaction: Transaction) -> Promise<T>): Promise<T>
    open fun batch(): WriteBatch
    open var app: firebase.app.App
    open fun clearPersistence(): Promise<Unit>
    open fun enableNetwork(): Promise<Unit>
    open fun disableNetwork(): Promise<Unit>
    open fun waitForPendingWrites(): Promise<Unit>
    open fun onSnapshotsInSync(observer: `T$27`): () -> Unit
    open fun onSnapshotsInSync(onSync: () -> Unit): () -> Unit
    open fun terminate(): Promise<Unit>
    open var INTERNAL: `T$28`
}

open external class GeoPoint(latitude: Number, longitude: Number) {
    open var latitude: Number
    open var longitude: Number
    open fun isEqual(other: GeoPoint): Boolean
}

open external class Timestamp(seconds: Number, nanoseconds: Number) {
    open var seconds: Number
    open var nanoseconds: Number
    open fun toDate(): Date
    open fun toMillis(): Number
    open fun isEqual(other: Timestamp): Boolean

    companion object {
        fun now(): Timestamp
        fun fromDate(date: Date): Timestamp
        fun fromMillis(milliseconds: Number): Timestamp
    }
}

open external class Blob {
    open fun toBase64(): String
    open fun toUint8Array(): Uint8Array
    open fun isEqual(other: Blob): Boolean

    companion object {
        fun fromBase64String(base64: String): Blob
        fun fromUint8Array(array: Uint8Array): Blob
    }
}

open external class Transaction {
    open fun get(documentRef: DocumentReference): Promise<DocumentSnapshot>
    open fun set(documentRef: DocumentReference, data: DocumentData, options: SetOptions? = definedExternally): Transaction
    open fun update(documentRef: DocumentReference, data: UpdateData): Transaction
    open fun update(documentRef: DocumentReference, field: String, value: Any, vararg moreFieldsAndValues: Any): Transaction
    open fun update(documentRef: DocumentReference, field: FieldPath, value: Any, vararg moreFieldsAndValues: Any): Transaction
    open fun delete(documentRef: DocumentReference): Transaction
}

open external class WriteBatch {
    open fun set(documentRef: DocumentReference, data: DocumentData, options: SetOptions? = definedExternally): WriteBatch
    open fun update(documentRef: DocumentReference, data: UpdateData): WriteBatch
    open fun update(documentRef: DocumentReference, field: String, value: Any, vararg moreFieldsAndValues: Any): WriteBatch
    open fun update(documentRef: DocumentReference, field: FieldPath, value: Any, vararg moreFieldsAndValues: Any): WriteBatch
    open fun delete(documentRef: DocumentReference): WriteBatch
    open fun commit(): Promise<Unit>
}

external interface SnapshotListenOptions {
    var includeMetadataChanges: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SetOptions {
    var merge: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var mergeFields: Array<dynamic /* String | FieldPath */>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface GetOptions {
    var source: dynamic /* 'default' | 'server' | 'cache' */
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$29` {
    var next: ((snapshot: DocumentSnapshot) -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
    var error: ((error: FirestoreError) -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
    var complete: (() -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$30` {
    var next: ((snapshot: DocumentSnapshot) -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
    var error: ((error: Error) -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
    var complete: (() -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
}

open external class DocumentReference {
    open var id: String
    open var firestore: Firestore
    open var parent: CollectionReference
    open var path: String
    open fun collection(collectionPath: String): CollectionReference
    open fun isEqual(other: DocumentReference): Boolean
    open fun set(data: DocumentData, options: SetOptions? = definedExternally): Promise<Unit>
    open fun update(data: UpdateData): Promise<Unit>
    open fun update(field: String, value: Any, vararg moreFieldsAndValues: Any): Promise<Unit>
    open fun update(field: FieldPath, value: Any, vararg moreFieldsAndValues: Any): Promise<Unit>
    open fun delete(): Promise<Unit>
    open fun get(options: GetOptions? = definedExternally): Promise<DocumentSnapshot>
    open fun onSnapshot(observer: `T$29`): () -> Unit
    open fun onSnapshot(options: SnapshotListenOptions, observer: `T$30`): () -> Unit
    open fun onSnapshot(onNext: (snapshot: DocumentSnapshot) -> Unit, onError: ((error: Error) -> Unit)? = definedExternally, onCompletion: (() -> Unit)? = definedExternally): () -> Unit
    open fun onSnapshot(options: SnapshotListenOptions, onNext: (snapshot: DocumentSnapshot) -> Unit, onError: ((error: Error) -> Unit)? = definedExternally, onCompletion: (() -> Unit)? = definedExternally): () -> Unit
}

external interface SnapshotOptions {
    var serverTimestamps: dynamic /* 'estimate' | 'previous' | 'none' */
        get() = definedExternally
        set(value) = definedExternally
}

external interface SnapshotMetadata {
    var hasPendingWrites: Boolean
    var fromCache: Boolean
    fun isEqual(other: SnapshotMetadata): Boolean
}

open external class DocumentSnapshot {
    open var exists: Boolean
    open var ref: DocumentReference
    open var id: String
    open var metadata: SnapshotMetadata
    open fun data(options: SnapshotOptions? = definedExternally): DocumentData?
    open fun get(fieldPath: String, options: SnapshotOptions? = definedExternally): Any
    open fun get(fieldPath: FieldPath, options: SnapshotOptions? = definedExternally): Any
    open fun isEqual(other: DocumentSnapshot): Boolean
}

open external class QueryDocumentSnapshot : DocumentSnapshot {
    override fun data(options: SnapshotOptions?): DocumentData
}

external interface `T$31` {
    var next: ((snapshot: QuerySnapshot) -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
    var error: ((error: Error) -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
    var complete: (() -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
}

open external class Query {
    open var firestore: Firestore
    open fun where(fieldPath: String, opStr: dynamic /* '<' | '<=' | '==' | '>=' | '>' | 'array-contains' | 'in' | 'array-contains-any' */, value: Any): Query
    open fun where(fieldPath: FieldPath, opStr: dynamic /* '<' | '<=' | '==' | '>=' | '>' | 'array-contains' | 'in' | 'array-contains-any' */, value: Any): Query
    open fun orderBy(fieldPath: String, directionStr: String /* 'desc' */ = definedExternally): Query
    open fun orderBy(fieldPath: String, directionStr: String /* 'asc' */ = definedExternally): Query
    open fun orderBy(fieldPath: FieldPath, directionStr: String /* 'desc' */ = definedExternally): Query
    open fun orderBy(fieldPath: FieldPath, directionStr: String /* 'asc' */ = definedExternally): Query
    open fun limit(limit: Number): Query
    open fun limitToLast(limit: Number): Query
    open fun startAt(snapshot: DocumentSnapshot): Query
    open fun startAt(vararg fieldValues: Any): Query
    open fun startAfter(snapshot: DocumentSnapshot): Query
    open fun startAfter(vararg fieldValues: Any): Query
    open fun endBefore(snapshot: DocumentSnapshot): Query
    open fun endBefore(vararg fieldValues: Any): Query
    open fun endAt(snapshot: DocumentSnapshot): Query
    open fun endAt(vararg fieldValues: Any): Query
    open fun isEqual(other: Query): Boolean
    open fun get(options: GetOptions? = definedExternally): Promise<QuerySnapshot>
    open fun onSnapshot(observer: `T$31`): () -> Unit
    open fun onSnapshot(options: SnapshotListenOptions, observer: `T$31`): () -> Unit
    open fun onSnapshot(onNext: (snapshot: QuerySnapshot) -> Unit, onError: ((error: Error) -> Unit)? = definedExternally, onCompletion: (() -> Unit)? = definedExternally): () -> Unit
    open fun onSnapshot(options: SnapshotListenOptions, onNext: (snapshot: QuerySnapshot) -> Unit, onError: ((error: Error) -> Unit)? = definedExternally, onCompletion: (() -> Unit)? = definedExternally): () -> Unit
    open fun orderBy(fieldPath: String): Query
    open fun orderBy(fieldPath: FieldPath): Query
}

open external class QuerySnapshot {
    open var query: Query
    open var metadata: SnapshotMetadata
    open var docs: Array<QueryDocumentSnapshot>
    open var size: Number
    open var empty: Boolean
    open fun docChanges(options: SnapshotListenOptions? = definedExternally): Array<DocumentChange>
    open fun forEach(callback: (result: QueryDocumentSnapshot) -> Unit, thisArg: Any? = definedExternally)
    open fun isEqual(other: QuerySnapshot): Boolean
}

external interface DocumentChange {
    var type: dynamic /* 'added' | 'removed' | 'modified' */
        get() = definedExternally
        set(value) = definedExternally
    var doc: QueryDocumentSnapshot
    var oldIndex: Number
    var newIndex: Number
}

open external class CollectionReference : Query {
    open var id: String
    open var parent: DocumentReference?
    open var path: String
    open fun doc(documentPath: String? = definedExternally): DocumentReference
    open fun add(data: DocumentData): Promise<DocumentReference>
    open fun isEqual(other: CollectionReference): Boolean
}

open external class FieldValue {
    open fun isEqual(other: FieldValue): Boolean

    companion object {
        fun serverTimestamp(): FieldValue
        fun delete(): FieldValue
        fun arrayUnion(vararg elements: Any): FieldValue
        fun arrayRemove(vararg elements: Any): FieldValue
        fun increment(n: Number): FieldValue
    }
}

open external class FieldPath(vararg fieldNames: String) {
    open fun isEqual(other: FieldPath): Boolean

    companion object {
        fun documentId(): FieldPath
    }
}

external interface FirestoreError {
    var code: dynamic /* 'cancelled' | 'unknown' | 'invalid-argument' | 'deadline-exceeded' | 'not-found' | 'already-exists' | 'permission-denied' | 'resource-exhausted' | 'failed-precondition' | 'aborted' | 'out-of-range' | 'unimplemented' | 'internal' | 'unavailable' | 'data-loss' | 'unauthenticated' */
        get() = definedExternally
        set(value) = definedExternally
    var message: String
    var name: String
    var stack: String?
        get() = definedExternally
        set(value) = definedExternally
}
