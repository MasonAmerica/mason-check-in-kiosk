package com.bymason.kiosk.checkin.feature.identity.holders

import android.text.Editable
import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.bymason.kiosk.checkin.R
import com.bymason.kiosk.checkin.feature.identity.FieldState
import com.bymason.kiosk.checkin.feature.identity.IdentityViewModel
import com.google.android.material.textfield.TextInputLayout
import java.util.Locale

abstract class StringIdentityViewHolderBase(
        vm: IdentityViewModel,
        itemView: View,
        private val inputLayout: TextInputLayout
) : IdentityViewHolderBase(vm, itemView), View.OnFocusChangeListener {
    init {
        inputLayout.addOnEditTextAttachedListener {
            val editText = it.editText!!
            editText.onFocusChangeListener = this
            editText.doAfterTextChanged {
                vm.onFieldChanged(state, it.stringify(), editText.hasFocus())
            }
        }
    }

    override fun updateValue(new: FieldState) {
        inputLayout.hint = if (new.field.required) {
            new.field.name
        } else {
            itemView.context.getString(
                    R.string.kiosk_checkin_identity_field_hint_title,
                    state.field.name
            )
        }
        inputLayout.editText?.apply {
            if (new.value != text.stringify()) setText(new.value)
        }
    }

    override fun updateErrorStatus(isValid: Boolean) {
        inputLayout.error = if (isValid) {
            null
        } else {
            itemView.context.getString(
                    R.string.kiosk_checkin_identity_field_invalid_error,
                    state.field.name.toLowerCase(Locale.getDefault())
            )
        }
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        vm.onFieldChanged(state, inputLayout.editText?.text.stringify(), hasFocus)
    }

    private fun Editable?.stringify() = takeIf { !it.isNullOrBlank() }?.toString()
}
