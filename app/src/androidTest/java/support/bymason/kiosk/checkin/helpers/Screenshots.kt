package support.bymason.kiosk.checkin.helpers

import android.app.Activity
import android.view.ViewGroup
import android.view.ViewGroup.FOCUS_BLOCK_DESCENDANTS
import android.widget.EditText
import androidx.core.view.children
import androidx.test.core.app.ActivityScenario
import com.facebook.testing.screenshot.Screenshot
import support.bymason.kiosk.checkin.core.ui.hideKeyboard
import java.util.concurrent.Semaphore

fun <T : Activity> ActivityScenario<T>.takeScreenshot() {
    lateinit var activity: Activity
    onActivity {
        activity = it
    }

    activity.stabilizeTests()
    Thread.sleep(750) // Wait for animations to finish

    // The automated screenshot naming is based off of stack trace analysis. We can't put this in
    // the onActivity lambda because that gets posted to the main thread.
    Screenshot.snapActivity(activity).record()
}

private fun Activity.stabilizeTests() {
    val semaphore = Semaphore(0)
    runOnUiThread {
        findViewById<ViewGroup>(android.R.id.content).stabilizeTests()

        semaphore.release()
    }
    semaphore.acquire()
}

private fun ViewGroup.stabilizeTests() {
    hideCursors()
    hideKeyboard()
}

private fun ViewGroup.hideCursors() {
    for (child in children) {
        if (child is ViewGroup) {
            child.descendantFocusability = FOCUS_BLOCK_DESCENDANTS
            child.hideCursors()
        }

        if (child is EditText) {
            child.clearFocus()
            child.isCursorVisible = false
        }
    }
}
