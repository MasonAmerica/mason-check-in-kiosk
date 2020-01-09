package com.bymason.kiosk.checkin.feature.identity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.EditText
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bymason.kiosk.checkin.R
import com.bymason.kiosk.checkin.core.data.CheckInApi
import com.bymason.kiosk.checkin.core.data.DispatcherProvider
import com.bymason.kiosk.checkin.core.ui.FragmentBase
import com.bymason.kiosk.checkin.core.ui.LifecycleAwareLazy
import com.bymason.kiosk.checkin.core.ui.doOnImeDone
import com.bymason.kiosk.checkin.core.ui.onDestroy
import com.bymason.kiosk.checkin.core.ui.showKeyboard
import com.bymason.kiosk.checkin.databinding.IdentityFragmentBinding
import kotlinx.coroutines.flow.collect

class IdentityFragment(
        dispatchers: DispatcherProvider,
        api: CheckInApi
) : FragmentBase(R.layout.identity_fragment) {
    private val vm by viewModels<IdentityViewModel> {
        IdentityViewModel.Factory(dispatchers, DefaultIdentityRepository(dispatchers, api))
    }

    private val binding by LifecycleAwareLazy {
        IdentityFragmentBinding.bind(requireView())
    } onDestroy {
        fields.viewTreeObserver.removeOnGlobalLayoutListener(installer)
    }
    private val progress: View? by lazy { requireActivity().findViewById<View>(R.id.progress) }
    private val installer = KeyboardInstaller()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            vm.actions.collect { onActionRequested(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.next.setOnClickListener { vm.onContinue() }

        val adapter = IdentityAdapter(vm)
        binding.fields.adapter = adapter
        binding.fields.itemAnimator = null
        binding.fields.viewTreeObserver.addOnGlobalLayoutListener(installer)

        vm.state.observe(viewLifecycleOwner) {
            onViewStateChanged(it, adapter)
        }
    }

    private fun onActionRequested(action: IdentityViewModel.Action) {
        when (action) {
            is IdentityViewModel.Action.Navigate -> findNavController().navigate(action.directions)
        }
    }

    private fun onViewStateChanged(state: IdentityViewModel.State, adapter: IdentityAdapter) {
        progress?.isVisible = state.isLoading
        binding.next.isEnabled = state.isContinueButtonEnabled
        adapter.submitList(state.fieldStates)
    }

    inner class KeyboardInstaller : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            val lm = binding.fields.layoutManager ?: return
            val first = lm.getChildAt(0) ?: return
            val last = lm.getChildAt(lm.childCount - 1) ?: return
            val firstInput = (first as? ViewGroup)?.findEditText()
            val lastInput = (last as? ViewGroup)?.findEditText()

            firstInput?.requestFocus()
            firstInput?.showKeyboard()

            lastInput?.doOnImeDone { vm.onContinue() }

            binding.fields.viewTreeObserver.removeOnGlobalLayoutListener(this)
        }

        private fun ViewGroup.findEditText(): EditText? {
            for (child in children) {
                if (child is ViewGroup) {
                    val inner = child.findEditText()
                    if (inner != null) return inner
                } else if (child is EditText) {
                    return child
                }
            }
            return null
        }
    }
}
