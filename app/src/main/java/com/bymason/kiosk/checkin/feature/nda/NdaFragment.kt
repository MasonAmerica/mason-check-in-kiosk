package com.bymason.kiosk.checkin.feature.nda

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bymason.kiosk.checkin.R
import com.bymason.kiosk.checkin.core.ui.FragmentBase
import com.bymason.kiosk.checkin.core.ui.LifecycleAwareLazy
import com.bymason.kiosk.checkin.databinding.NdaFragmentBinding
import kotlinx.coroutines.flow.collect

class NdaFragment(
        repository: NdaRepository
) : FragmentBase(R.layout.nda_fragment) {
    private val args by navArgs<NdaFragmentArgs>()

    private val vm by viewModels<NdaViewModel> {
        NdaViewModel.Factory(repository, args.sessionId)
    }
    private val binding by LifecycleAwareLazy { NdaFragmentBinding.bind(requireView()) }
    private val progress: View? by lazy { requireActivity().findViewById<View>(R.id.progress) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            vm.actions.collect { onActionRequested(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.web.webViewClient = vm.createWebViewClient()
        binding.web.webChromeClient = vm.createWebChromeClient()
        binding.web.settings.javaScriptEnabled = true
        savedInstanceState?.let { binding.web.restoreState(it) }

        binding.finishCheckInHint.setOnTouchListener { _, e ->
            if (e.action == MotionEvent.ACTION_UP) {
                vm.onNdaSigned()
            }
            true
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            vm.viewActions.collect { onViewActionRequested(it) }
        }
        vm.state.observe(viewLifecycleOwner) {
            onViewStateChanged(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (view == null) return
        binding.web.saveState(outState)
    }

    private fun onActionRequested(action: NdaViewModel.Action) {
        when (action) {
            is NdaViewModel.Action.Navigate -> findNavController().navigate(action.directions)
            NdaViewModel.Action.SignNda -> vm.onNdaSigningRequested()
        }
    }

    private fun onViewActionRequested(action: NdaViewModel.ViewAction) {
        when (action) {
            is NdaViewModel.ViewAction.VisitPage -> binding.web.loadUrl(action.url)
        }
    }

    private fun onViewStateChanged(state: NdaViewModel.State) {
        progress?.isVisible = state.isLoading
        binding.web.isVisible = state.isWebViewVisible
        binding.finishCheckInHint.isVisible = state.isFinishButtonVisible
    }
}
