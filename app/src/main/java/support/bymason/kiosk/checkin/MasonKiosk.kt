package support.bymason.kiosk.checkin

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import support.bymason.kiosk.checkin.core._globalContext

class MasonKiosk : Application() {
    override fun onCreate() {
        super.onCreate()
        _globalContext = this
    }

    companion object {
        init {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}
