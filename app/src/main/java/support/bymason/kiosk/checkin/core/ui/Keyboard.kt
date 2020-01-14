package support.bymason.kiosk.checkin.core.ui

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.getSystemService
import support.bymason.kiosk.checkin.core.MasonKiosk

val inputMethodManager by lazy { checkNotNull(MasonKiosk.getSystemService<InputMethodManager>()) }

fun View.showKeyboard() {
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

inline fun EditText.doOnImeDone(
        crossinline listener: () -> Unit
) = setOnEditorActionListener { _, actionId, event: KeyEvent? ->
    if (event?.keyCode == KeyEvent.KEYCODE_ENTER) {
        if (event.action == KeyEvent.ACTION_UP) listener()

        // We need to return true even if we didn't handle the event to continue
        // receiving future callbacks.
        true
    } else if (actionId == EditorInfo.IME_ACTION_DONE) {
        listener()
        true
    } else {
        false
    }
}
