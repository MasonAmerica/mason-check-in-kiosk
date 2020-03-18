package support.bymason.kiosk.checkin.feature.signin

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import support.bymason.kiosk.checkin.HomeActivity
import support.bymason.kiosk.checkin.R
import support.bymason.kiosk.checkin.helpers.UiTestBase
import support.bymason.kiosk.checkin.helpers.takeScreenshot

@RunWith(AndroidJUnit4::class)
class SignInFragmentUiTest : UiTestBase() {
    @Test
    fun diffEmptySignInScreen() {
        val scenario = launchActivity<HomeActivity>()

        scenario.takeScreenshot()
    }

    @Test
    fun diffFilledOutSignInScreen() {
        val scenario = launchActivity<HomeActivity>()

        onView(withId(R.id.email)).perform(typeText("hot@sauce.com"))
        onView(withId(R.id.password)).perform(typeText("password"))

        scenario.takeScreenshot()
    }
}
