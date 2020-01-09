package com.bymason.kiosk.checkin.feature.welcome

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bymason.kiosk.checkin.R
import com.bymason.kiosk.checkin.core.data.CheckInApi
import com.bymason.kiosk.checkin.core.data.DispatcherProvider
import com.bymason.kiosk.checkin.core.ui.FragmentBase
import com.bymason.kiosk.checkin.core.ui.LifecycleAwareLazy
import com.bymason.kiosk.checkin.databinding.WelcomeFragmentBinding
import com.google.firebase.nongmsauth.FirebaseAuthCompat
import kotlinx.coroutines.flow.collect

class WelcomeFragment(
        dispatchers: DispatcherProvider,
        auth: FirebaseAuthCompat,
        api: CheckInApi
) : FragmentBase(R.layout.welcome_fragment) {
    private val vm by viewModels<WelcomeViewModel> {
        WelcomeViewModel.Factory(auth, DefaultWelcomeRepository(dispatchers, api))
    }
    private val binding by LifecycleAwareLazy { WelcomeFragmentBinding.bind(requireView()) }
    private val progress: View? by lazy { requireActivity().findViewById<View>(R.id.progress) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            vm.actions.collect { onActionRequested(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.setOnTouchListener { _, e ->
            if (e.action == MotionEvent.ACTION_UP) vm.onTap()
            true
        }

        vm.state.observe(viewLifecycleOwner) {
            onViewStateChanged(it)
        }
    }

    private fun onActionRequested(action: WelcomeViewModel.Action) {
        when (action) {
            is WelcomeViewModel.Action.Navigate ->
                findNavController().navigate(action.directions)
        }
    }

    private fun onViewStateChanged(state: WelcomeViewModel.State) {
        progress?.isVisible = state.isLoading
        binding.welcomeTitle.text = state.welcomeText
        Glide.with(this)
                .load(state.companyLogoUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.logo)
    }
}
