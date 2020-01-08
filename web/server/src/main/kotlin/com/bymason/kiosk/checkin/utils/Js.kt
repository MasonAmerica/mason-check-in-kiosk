package com.bymason.kiosk.checkin.utils

@Suppress("UNUSED_VARIABLE", "UNUSED_PARAMETER")
fun <T> createInstance(type: dynamic, vararg args: dynamic): T {
    val argsArray = (listOf<Any?>(null) + args).toTypedArray()
    return js("new (Function.prototype.bind.apply(type, argsArray))").unsafeCast<T>()
}
