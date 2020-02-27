package support.bymason.kiosk.checkin.feature.report

import androidx.navigation.findNavController
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import support.bymason.kiosk.checkin.HomeActivity
import support.bymason.kiosk.checkin.R
import support.bymason.kiosk.checkin.helpers.UiTestBase
import support.bymason.kiosk.checkin.helpers.takeScreenshot

@RunWith(AndroidJUnit4::class)
class ReportFragmentUiTest : UiTestBase() {
    @Test
    fun diffReport() {
        val scenario = launchActivity<HomeActivity>()

        scenario.onActivity { activity ->
            val controller = activity.findNavController(R.id.content)

            controller.navigate(R.id.report)
        }

        scenario.takeScreenshot()
    }
}
