@file:Suppress(
        "INTERFACE_WITH_SUPERCLASS",
        "OVERRIDING_FINAL_MEMBER",
        "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
        "CONFLICTING_OVERLOADS",
        "unused"
)

package firebase.https

import firebase.functions.EventContext
import kotlin.js.Json
import kotlin.js.Promise

external class Pubsub {
    fun topic(topic: String): TopicBuilder = definedExternally
    fun schedule(schedule: String): ScheduleBuilder = definedExternally
}

external class TopicBuilder {
    fun onPublish(handler: (message: Message, context: EventContext) -> Promise<*>?): dynamic = definedExternally
}

external class ScheduleBuilder {
    fun onRun(handler: (context: EventContext) -> Promise<*>?): dynamic = definedExternally
}

external class Message(data: Any) {
    val data: String = definedExternally
    val json: Json = definedExternally
}
