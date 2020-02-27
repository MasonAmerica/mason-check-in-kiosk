package support.bymason.kiosk.checkin.helpers

import android.app.Activity
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.children
import androidx.test.core.app.ActivityScenario
import com.facebook.testing.screenshot.Screenshot

fun <T : Activity> ActivityScenario<T>.takeScreenshot() {
    lateinit var activity: Activity
    onActivity {
        activity = it
    }

    activity.hideCursors()
    Thread.sleep(750) // Wait for animations to finish

    // The automated screenshot naming is based off of stack trace analysis. We can't put this in
    // the onActivity lambda because that gets posted to the main thread.
    Screenshot.snapActivity(activity).record()
}

private fun Activity.hideCursors() {
    runOnUiThread { findViewById<ViewGroup>(android.R.id.content).hideCursors() }
}

private fun ViewGroup.hideCursors() {
    for (child in children) {
        if (child is ViewGroup) child.hideCursors()
        if (child is EditText) child.isCursorVisible = false
    }
}
