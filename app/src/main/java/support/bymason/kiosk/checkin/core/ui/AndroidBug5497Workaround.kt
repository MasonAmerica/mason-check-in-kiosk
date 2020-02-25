package support.bymason.kiosk.checkin.core.ui

import android.app.Activity
import android.graphics.Rect
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout

class AndroidBug5497Workaround(activity: Activity) {
    private val contentContainer: ViewGroup = activity.findViewById(android.R.id.content)
    private val rootView = contentContainer.getChildAt(0)
    private val rootViewLayout = rootView.layoutParams as FrameLayout.LayoutParams
    private val listener = ViewTreeObserver.OnGlobalLayoutListener { possiblyResizeChildOfContent() }

    private val contentAreaOfWindowBounds = Rect()
    private var usableHeightPrevious = 0

    fun addListener() {
        rootView.viewTreeObserver.addOnGlobalLayoutListener(listener)
    }

    fun removeListener() {
        rootView.viewTreeObserver.removeOnGlobalLayoutListener(listener)
    }

    private fun possiblyResizeChildOfContent() {
        contentContainer.getWindowVisibleDisplayFrame(contentAreaOfWindowBounds)
        val usableHeightNow = contentAreaOfWindowBounds.height()
        if (usableHeightNow != usableHeightPrevious) {
            rootViewLayout.height = usableHeightNow
            // Change the bounds of the root view to prevent gap between keyboard and content,
            // and top of content positioned above top screen edge.
            rootView.layout(
                    contentAreaOfWindowBounds.left,
                    contentAreaOfWindowBounds.top,
                    contentAreaOfWindowBounds.right,
                    contentAreaOfWindowBounds.bottom
            )
            rootView.requestLayout()

            usableHeightPrevious = usableHeightNow
        }
    }
}
