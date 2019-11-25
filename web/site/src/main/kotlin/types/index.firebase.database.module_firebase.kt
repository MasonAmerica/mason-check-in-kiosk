@file:JsQualifier("firebase.database")
@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION")

package firebase.database

import kotlin.js.Promise

external interface DataSnapshot {
    fun child(path: String): firebase.database.DataSnapshot
    fun exists(): Boolean
    fun exportVal(): Any
    fun forEach(action: (a: firebase.database.DataSnapshot) -> dynamic): Boolean
    fun getPriority(): dynamic /* String | Number | Nothing? */
    fun hasChild(path: String): Boolean
    fun hasChildren(): Boolean
    var key: String?
        get() = definedExternally
        set(value) = definedExternally

    fun numChildren(): Number
    fun `val`(): Any
    var ref: firebase.database.Reference
    fun toJSON(): Any?
}

external interface Database {
    var app: firebase.app.App
    fun goOffline(): Any
    fun goOnline(): Any
    fun ref(path: String? = definedExternally): firebase.database.Reference
    fun refFromURL(url: String): firebase.database.Reference
}

external interface OnDisconnect {
    fun cancel(onComplete: ((a: Error?) -> Any)? = definedExternally): Promise<Any>
    fun remove(onComplete: ((a: Error?) -> Any)? = definedExternally): Promise<Any>
    fun set(value: Any, onComplete: ((a: Error?) -> Any)? = definedExternally): Promise<Any>
    fun setWithPriority(value: Any, priority: Number, onComplete: ((a: Error?) -> Any)? = definedExternally): Promise<Any>
    fun setWithPriority(value: Any, priority: String, onComplete: ((a: Error?) -> Any)? = definedExternally): Promise<Any>
    fun setWithPriority(value: Any, priority: Nothing?, onComplete: ((a: Error?) -> Any)? = definedExternally): Promise<Any>
    fun update(values: Any, onComplete: ((a: Error?) -> Any)? = definedExternally): Promise<Any>
}

external interface Query {
    fun endAt(value: Number, key: String? = definedExternally): firebase.database.Query
    fun endAt(value: String, key: String? = definedExternally): firebase.database.Query
    fun endAt(value: Boolean, key: String? = definedExternally): firebase.database.Query
    fun endAt(value: Nothing?, key: String? = definedExternally): firebase.database.Query
    fun equalTo(value: Number, key: String? = definedExternally): firebase.database.Query
    fun equalTo(value: String, key: String? = definedExternally): firebase.database.Query
    fun equalTo(value: Boolean, key: String? = definedExternally): firebase.database.Query
    fun equalTo(value: Nothing?, key: String? = definedExternally): firebase.database.Query
    fun isEqual(other: firebase.database.Query?): Boolean
    fun limitToFirst(limit: Number): firebase.database.Query
    fun limitToLast(limit: Number): firebase.database.Query
    fun off(eventType: String /* 'value' */ = definedExternally, callback: ((a: firebase.database.DataSnapshot, b: String?) -> Any)? = definedExternally, context: Any? = definedExternally)
    fun off(eventType: String /* 'child_added' */ = definedExternally, callback: ((a: firebase.database.DataSnapshot, b: String?) -> Any)? = definedExternally, context: Any? = definedExternally)
    fun off(eventType: String /* 'child_changed' */ = definedExternally, callback: ((a: firebase.database.DataSnapshot, b: String?) -> Any)? = definedExternally, context: Any? = definedExternally)
    fun off(eventType: String /* 'child_moved' */ = definedExternally, callback: ((a: firebase.database.DataSnapshot, b: String?) -> Any)? = definedExternally, context: Any? = definedExternally)
    fun off(eventType: String /* 'child_removed' */ = definedExternally, callback: ((a: firebase.database.DataSnapshot, b: String?) -> Any)? = definedExternally, context: Any? = definedExternally)
    fun on(eventType: String /* 'value' */, callback: (a: firebase.database.DataSnapshot, b: String?) -> Any, cancelCallbackOrContext: Any? = definedExternally, context: Any? = definedExternally): (a: firebase.database.DataSnapshot?, b: String?) -> Any
    fun on(eventType: String /* 'child_added' */, callback: (a: firebase.database.DataSnapshot, b: String?) -> Any, cancelCallbackOrContext: Any? = definedExternally, context: Any? = definedExternally): (a: firebase.database.DataSnapshot?, b: String?) -> Any
    fun on(eventType: String /* 'child_changed' */, callback: (a: firebase.database.DataSnapshot, b: String?) -> Any, cancelCallbackOrContext: Any? = definedExternally, context: Any? = definedExternally): (a: firebase.database.DataSnapshot?, b: String?) -> Any
    fun on(eventType: String /* 'child_moved' */, callback: (a: firebase.database.DataSnapshot, b: String?) -> Any, cancelCallbackOrContext: Any? = definedExternally, context: Any? = definedExternally): (a: firebase.database.DataSnapshot?, b: String?) -> Any
    fun on(eventType: String /* 'child_removed' */, callback: (a: firebase.database.DataSnapshot, b: String?) -> Any, cancelCallbackOrContext: Any? = definedExternally, context: Any? = definedExternally): (a: firebase.database.DataSnapshot?, b: String?) -> Any
    fun once(eventType: String /* 'value' */, successCallback: ((a: firebase.database.DataSnapshot, b: String?) -> Any)? = definedExternally, failureCallbackOrContext: ((a: Error) -> Unit)? = definedExternally, context: Any? = definedExternally): Promise<firebase.database.DataSnapshot>
    fun once(eventType: String /* 'value' */, successCallback: ((a: firebase.database.DataSnapshot, b: String?) -> Any)? = definedExternally, failureCallbackOrContext: Any? = definedExternally, context: Any? = definedExternally): Promise<firebase.database.DataSnapshot>
    fun once(eventType: String /* 'value' */, successCallback: ((a: firebase.database.DataSnapshot, b: String?) -> Any)? = definedExternally, failureCallbackOrContext: Nothing? = definedExternally, context: Any? = definedExternally): Promise<firebase.database.DataSnapshot>
    fun once(eventType: String /* 'child_added' */, successCallback: ((a: firebase.database.DataSnapshot, b: String?) -> Any)? = definedExternally, failureCallbackOrContext: ((a: Error) -> Unit)? = definedExternally, context: Any? = definedExternally): Promise<firebase.database.DataSnapshot>
    fun once(eventType: String /* 'child_added' */, successCallback: ((a: firebase.database.DataSnapshot, b: String?) -> Any)? = definedExternally, failureCallbackOrContext: Any? = definedExternally, context: Any? = definedExternally): Promise<firebase.database.DataSnapshot>
    fun once(eventType: String /* 'child_added' */, successCallback: ((a: firebase.database.DataSnapshot, b: String?) -> Any)? = definedExternally, failureCallbackOrContext: Nothing? = definedExternally, context: Any? = definedExternally): Promise<firebase.database.DataSnapshot>
    fun once(eventType: String /* 'child_changed' */, successCallback: ((a: firebase.database.DataSnapshot, b: String?) -> Any)? = definedExternally, failureCallbackOrContext: ((a: Error) -> Unit)? = definedExternally, context: Any? = definedExternally): Promise<firebase.database.DataSnapshot>
    fun once(eventType: String /* 'child_changed' */, successCallback: ((a: firebase.database.DataSnapshot, b: String?) -> Any)? = definedExternally, failureCallbackOrContext: Any? = definedExternally, context: Any? = definedExternally): Promise<firebase.database.DataSnapshot>
    fun once(eventType: String /* 'child_changed' */, successCallback: ((a: firebase.database.DataSnapshot, b: String?) -> Any)? = definedExternally, failureCallbackOrContext: Nothing? = definedExternally, context: Any? = definedExternally): Promise<firebase.database.DataSnapshot>
    fun once(eventType: String /* 'child_moved' */, successCallback: ((a: firebase.database.DataSnapshot, b: String?) -> Any)? = definedExternally, failureCallbackOrContext: ((a: Error) -> Unit)? = definedExternally, context: Any? = definedExternally): Promise<firebase.database.DataSnapshot>
    fun once(eventType: String /* 'child_moved' */, successCallback: ((a: firebase.database.DataSnapshot, b: String?) -> Any)? = definedExternally, failureCallbackOrContext: Any? = definedExternally, context: Any? = definedExternally): Promise<firebase.database.DataSnapshot>
    fun once(eventType: String /* 'child_moved' */, successCallback: ((a: firebase.database.DataSnapshot, b: String?) -> Any)? = definedExternally, failureCallbackOrContext: Nothing? = definedExternally, context: Any? = definedExternally): Promise<firebase.database.DataSnapshot>
    fun once(eventType: String /* 'child_removed' */, successCallback: ((a: firebase.database.DataSnapshot, b: String?) -> Any)? = definedExternally, failureCallbackOrContext: ((a: Error) -> Unit)? = definedExternally, context: Any? = definedExternally): Promise<firebase.database.DataSnapshot>
    fun once(eventType: String /* 'child_removed' */, successCallback: ((a: firebase.database.DataSnapshot, b: String?) -> Any)? = definedExternally, failureCallbackOrContext: Any? = definedExternally, context: Any? = definedExternally): Promise<firebase.database.DataSnapshot>
    fun once(eventType: String /* 'child_removed' */, successCallback: ((a: firebase.database.DataSnapshot, b: String?) -> Any)? = definedExternally, failureCallbackOrContext: Nothing? = definedExternally, context: Any? = definedExternally): Promise<firebase.database.DataSnapshot>
    fun orderByChild(path: String): firebase.database.Query
    fun orderByKey(): firebase.database.Query
    fun orderByPriority(): firebase.database.Query
    fun orderByValue(): firebase.database.Query
    var ref: firebase.database.Reference
    fun startAt(value: Number, key: String? = definedExternally): firebase.database.Query
    fun startAt(value: String, key: String? = definedExternally): firebase.database.Query
    fun startAt(value: Boolean, key: String? = definedExternally): firebase.database.Query
    fun startAt(value: Nothing?, key: String? = definedExternally): firebase.database.Query
    fun toJSON(): Any
    override fun toString(): String
    fun off()
    fun once(eventType: String /* 'value' */): Promise<firebase.database.DataSnapshot>
    fun once(eventType: String /* 'child_added' */): Promise<firebase.database.DataSnapshot>
    fun once(eventType: String /* 'child_changed' */): Promise<firebase.database.DataSnapshot>
    fun once(eventType: String /* 'child_moved' */): Promise<firebase.database.DataSnapshot>
    fun once(eventType: String /* 'child_removed' */): Promise<firebase.database.DataSnapshot>
}

external interface Reference : firebase.database.Query {
    fun child(path: String): firebase.database.Reference
    var key: String?
        get() = definedExternally
        set(value) = definedExternally

    fun onDisconnect(): firebase.database.OnDisconnect
    var parent: firebase.database.Reference?
        get() = definedExternally
        set(value) = definedExternally

    fun push(value: Any? = definedExternally, onComplete: ((a: Error?) -> Any)? = definedExternally): firebase.database.ThenableReference
    fun remove(onComplete: ((a: Error?) -> Any)? = definedExternally): Promise<Any>
    var root: firebase.database.Reference
    fun set(value: Any, onComplete: ((a: Error?) -> Any)? = definedExternally): Promise<Any>
    fun setPriority(priority: String, onComplete: (a: Error?) -> Any): Promise<Any>
    fun setPriority(priority: Number, onComplete: (a: Error?) -> Any): Promise<Any>
    fun setPriority(priority: Nothing?, onComplete: (a: Error?) -> Any): Promise<Any>
    fun setWithPriority(newVal: Any, newPriority: String, onComplete: ((a: Error?) -> Any)? = definedExternally): Promise<Any>
    fun setWithPriority(newVal: Any, newPriority: Number, onComplete: ((a: Error?) -> Any)? = definedExternally): Promise<Any>
    fun setWithPriority(newVal: Any, newPriority: Nothing?, onComplete: ((a: Error?) -> Any)? = definedExternally): Promise<Any>
    fun transaction(transactionUpdate: (a: Any) -> Any, onComplete: ((a: Error?, b: Boolean, c: firebase.database.DataSnapshot?) -> Any)? = definedExternally, applyLocally: Boolean? = definedExternally): Promise<Any>
    fun update(values: Any, onComplete: ((a: Error?) -> Any)? = definedExternally): Promise<Any>
}

external interface ThenableReference : firebase.database.Reference, Promise<Reference>

external fun enableLogging(logger: Boolean? = definedExternally, persistent: Boolean? = definedExternally): Any

external fun enableLogging(logger: ((a: String) -> Any)? = definedExternally, persistent: Boolean? = definedExternally): Any

external fun enableLogging(): Any
