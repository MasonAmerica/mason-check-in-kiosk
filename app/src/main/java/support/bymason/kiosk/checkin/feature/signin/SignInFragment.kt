package support.bymason.kiosk.checkin.feature.signin

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.nongmsauth.FirebaseAuthCompat
import kotlinx.coroutines.flow.collect
import support.bymason.kiosk.checkin.R
import support.bymason.kiosk.checkin.core.ui.FragmentBase
import support.bymason.kiosk.checkin.core.ui.LifecycleAwareLazy
import support.bymason.kiosk.checkin.core.ui.doOnImeDone
import support.bymason.kiosk.checkin.databinding.SignInFragmentBinding

class SignInFragment(
        auth: FirebaseAuthCompat
) : FragmentBase(R.layout.sign_in_fragment) {
    private val vm by viewModels<SignInFragmentViewModel> {
        SignInFragmentViewModel.Factory(auth)
    }
    private val binding by LifecycleAwareLazy { SignInFragmentBinding.bind(requireView()) }
    private val progress: View? by lazy { requireActivity().findViewById<View>(R.id.progress) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            vm.actions.collect { onActionRequested(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.email.doAfterTextChanged { vm.onEmailChanged(it.toString()) }
        binding.password.doAfterTextChanged { vm.onPasswordChanged(it.toString()) }

        binding.password.doOnImeDone { submit() }
        binding.submit.setOnClickListener { submit() }

        vm.state.observe(viewLifecycleOwner) {
            onViewStateChanged(it)
        }
    }

    private fun submit() {
        vm.onSubmit(binding.email.text.toString(), binding.password.text.toString())
    }

    private fun onActionRequested(action: SignInFragmentViewModel.Action) {
        when (action) {
            is SignInFragmentViewModel.Action.Navigate ->
                findNavController().navigate(action.directions)
        }
    }

    private fun onViewStateChanged(state: SignInFragmentViewModel.State) {
        progress?.isVisible = state.isLoading
        binding.submit.isEnabled = state.isSubmitButtonEnabled
    }
}
