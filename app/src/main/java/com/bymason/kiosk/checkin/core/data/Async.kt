package com.bymason.kiosk.checkin.core.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {
    val main: CoroutineDispatcher

    val default: CoroutineDispatcher

    val io: CoroutineDispatcher

    val unconfined: CoroutineDispatcher
}

object DefaultDispatcherProvider : DispatcherProvider {
    override val main = Dispatchers.Main

    override val default = Dispatchers.Default

    override val io = Dispatchers.IO

    override val unconfined = Dispatchers.Unconfined
}
