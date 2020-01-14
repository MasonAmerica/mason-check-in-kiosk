package support.bymason.kiosk.checkin.core

import android.app.Application
import android.content.Context

@Suppress("ObjectPropertyName")
lateinit var _globalContext: Application
val MasonKiosk: Context get() = _globalContext
