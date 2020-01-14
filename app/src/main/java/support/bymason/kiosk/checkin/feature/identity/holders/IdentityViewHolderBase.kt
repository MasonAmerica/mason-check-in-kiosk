package support.bymason.kiosk.checkin.feature.identity.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import support.bymason.kiosk.checkin.feature.identity.FieldState
import support.bymason.kiosk.checkin.feature.identity.IdentityViewModel

abstract class IdentityViewHolderBase(
        protected val vm: IdentityViewModel,
        itemView: View
) : RecyclerView.ViewHolder(itemView) {
    private lateinit var _state: FieldState
    protected val state get() = _state

    fun bind(state: FieldState) {
        _state = state

        updateValue(state)
        updateErrorStatus(!state.showError)
    }

    protected abstract fun updateValue(new: FieldState)

    protected abstract fun updateErrorStatus(isValid: Boolean)
}
