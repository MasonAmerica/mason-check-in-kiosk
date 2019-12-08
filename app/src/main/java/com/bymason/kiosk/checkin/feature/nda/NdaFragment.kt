package com.bymason.kiosk.checkin.feature.nda

import android.os.Bundle
import android.view.View
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

    private val vm by viewModels<NdaViewModel> { NdaViewModel.Factory(repository) }
    private val binding by LifecycleAwareLazy { NdaFragmentBinding.bind(requireView()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            vm.actions.collect { onActionRequested(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.web.webViewClient = vm.createWebViewClient()
        binding.web.settings.javaScriptEnabled = true
        binding.web.restoreState(savedInstanceState)

        binding.finish.setOnClickListener { vm.finish(args.employee, args.guest) }

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
            NdaViewModel.Action.SignNda -> vm.signNda(args.guest)
        }
    }

    private fun onViewActionRequested(action: NdaViewModel.ViewAction) {
        when (action) {
            is NdaViewModel.ViewAction.VisitPage -> binding.web.loadUrl(action.url)
        }
    }

    private fun onViewStateChanged(state: NdaViewModel.State) {
        binding.finish.isEnabled = state.isFinishButtonEnabled
    }
}
