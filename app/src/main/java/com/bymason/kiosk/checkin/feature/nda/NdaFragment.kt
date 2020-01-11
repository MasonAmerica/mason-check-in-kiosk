package com.bymason.kiosk.checkin.feature.nda

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bymason.kiosk.checkin.R
import com.bymason.kiosk.checkin.core.data.CheckInApi
import com.bymason.kiosk.checkin.core.ui.FragmentBase
import com.bymason.kiosk.checkin.core.ui.LifecycleAwareLazy
import com.bymason.kiosk.checkin.databinding.NdaFragmentBinding
import kotlinx.coroutines.flow.collect

class NdaFragment(
        api: CheckInApi
) : FragmentBase(R.layout.nda_fragment) {
    private val args by navArgs<NdaFragmentArgs>()

    private val vm by viewModels<NdaViewModel> {
        NdaViewModel.Factory(DefaultNdaRepository(api), args.sessionId)
    }
    private val binding by LifecycleAwareLazy { NdaFragmentBinding.bind(requireView()) }
    private val progress: View? by lazy { requireActivity().findViewById<View>(R.id.progress) }

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            vm.actions.collect { onActionRequested(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.root.webViewClient = vm.createWebViewClient()
        binding.root.webChromeClient = vm.createWebChromeClient()
        binding.root.settings.javaScriptEnabled = true
        savedInstanceState?.let { binding.root.restoreState(it) }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            vm.viewActions.collect { onViewActionRequested(it) }
        }
        vm.state.observe(viewLifecycleOwner) {
            onViewStateChanged(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.check_in_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_restart -> vm.onRestartRequested()
            else -> return false
        }

        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (view == null) return
        binding.root.saveState(outState)
    }

    private fun onActionRequested(action: NdaViewModel.Action) {
        when (action) {
            is NdaViewModel.Action.Navigate -> findNavController().navigate(action.directions)
            NdaViewModel.Action.SignNda -> vm.onNdaSigningRequested()
        }
    }

    private fun onViewActionRequested(action: NdaViewModel.ViewAction) {
        when (action) {
            is NdaViewModel.ViewAction.VisitPage -> binding.root.loadUrl(action.url)
        }
    }

    private fun onViewStateChanged(state: NdaViewModel.State) {
        progress?.isVisible = state.isLoading
    }
}
