package support.bymason.kiosk.checkin.helpers

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.Before

abstract class UiTestBase {
    @Before
    fun fixUiAutomatorNotConnected() {
        // For whatever reason, this fixes `RuntimeException: Error while connecting UiAutomation`
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }
}
