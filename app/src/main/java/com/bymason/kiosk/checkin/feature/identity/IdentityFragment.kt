package com.bymason.kiosk.checkin.feature.identity

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bymason.kiosk.checkin.R
import com.bymason.kiosk.checkin.core.model.Guest
import com.bymason.kiosk.checkin.core.ui.FragmentBase
import com.bymason.kiosk.checkin.core.ui.LifecycleAwareLazy
import com.bymason.kiosk.checkin.core.ui.doOnImeDone
import com.bymason.kiosk.checkin.core.ui.showKeyboard
import com.bymason.kiosk.checkin.databinding.IdentityFragmentBinding

class IdentityFragment : FragmentBase(R.layout.identity_fragment), View.OnFocusChangeListener {
    private val vm by viewModels<IdentityViewModel>()
    private val binding by LifecycleAwareLazy { IdentityFragmentBinding.bind(requireView()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.name.requestFocus()
        binding.name.showKeyboard()
        binding.name.onFocusChangeListener = this
        binding.name.doAfterTextChanged { updateFormValidityStatus() }

        binding.email.onFocusChangeListener = this
        binding.email.doAfterTextChanged { updateFormValidityStatus() }
        binding.email.doOnImeDone { next() }

        binding.next.setOnClickListener { next() }

        updateFormValidityStatus()
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        when {
            v === binding.name -> if (hasFocus) {
                binding.nameLayout.error = null
            } else {
                validateName()
            }
            v === binding.email -> if (hasFocus) {
                binding.emailLayout.error = null
            } else {
                validateEmail()
            }
        }
    }

    private fun updateFormValidityStatus() {
        binding.next.isEnabled =
                vm.isNameValid(extractName()) &&
                        vm.isEmailValid(extractEmail())
    }

    private fun extractName() = binding.name.text?.toString()

    private fun extractEmail() = binding.email.text?.toString()

    private fun validateName() {
        binding.nameLayout.error = if (vm.isNameValid(extractName())) {
            null
        } else {
            getString(R.string.kiosk_checkin_identity_name_invalid_error)
        }
    }

    private fun validateEmail() {
        binding.emailLayout.error = if (vm.isEmailValid(extractEmail())) {
            null
        } else {
            getString(R.string.kiosk_checkin_identity_email_invalid_error)
        }
    }

    private fun next() {
        if (binding.next.isEnabled) {
            findNavController().navigate(IdentityFragmentDirections.next(Guest(
                    extractName()!!, extractEmail()!!)))
        }
    }
}
