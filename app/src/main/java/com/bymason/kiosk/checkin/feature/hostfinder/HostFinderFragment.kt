package com.bymason.kiosk.checkin.feature.hostfinder

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bymason.kiosk.checkin.R
import com.bymason.kiosk.checkin.core.data.CheckInApi
import com.bymason.kiosk.checkin.core.data.DispatcherProvider
import com.bymason.kiosk.checkin.core.ui.FragmentBase
import com.bymason.kiosk.checkin.core.ui.LifecycleAwareLazy
import com.bymason.kiosk.checkin.core.ui.hideKeyboard
import com.bymason.kiosk.checkin.core.ui.onDestroy
import com.bymason.kiosk.checkin.databinding.HostFinderFragmentBinding
import kotlinx.coroutines.flow.collect

class HostFinderFragment(
        dispatchers: DispatcherProvider,
        api: CheckInApi
) : FragmentBase(R.layout.host_finder_fragment) {
    private val args by navArgs<HostFinderFragmentArgs>()

    private val vm by viewModels<HostFinderViewModel> {
        HostFinderViewModel.Factory(DefaultHostRepository(dispatchers, api), args.sessionId)
    }
    private val binding by LifecycleAwareLazy {
        HostFinderFragmentBinding.bind(requireView())
    } onDestroy {
        search.clearFocus()
        search.hideKeyboard()
    }
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
        binding.search.doAfterTextChanged {
            vm.onSearch(it?.toString())
        }

        val adapter = HostAdapter(this, vm)
        binding.hosts.adapter = adapter

        vm.state.observe(viewLifecycleOwner) {
            onViewStateChanged(it, adapter)
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

    private fun onActionRequested(action: HostFinderViewModel.Action) {
        when (action) {
            is HostFinderViewModel.Action.Navigate ->
                findNavController().navigate(action.directions)
        }
    }

    private fun onViewStateChanged(state: HostFinderViewModel.State, adapter: HostAdapter) {
        progress?.isVisible = state.isLoading
        binding.noHostsHint.isVisible = state.isSearchHintVisible
        adapter.submitList(state.hosts)
    }
}
