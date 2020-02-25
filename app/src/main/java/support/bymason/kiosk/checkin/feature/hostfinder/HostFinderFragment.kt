package support.bymason.kiosk.checkin.feature.hostfinder

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.flow.collect
import support.bymason.kiosk.checkin.R
import support.bymason.kiosk.checkin.core.data.CheckInApi
import support.bymason.kiosk.checkin.core.data.DispatcherProvider
import support.bymason.kiosk.checkin.core.ui.FragmentBase
import support.bymason.kiosk.checkin.core.ui.LifecycleAwareLazy
import support.bymason.kiosk.checkin.core.ui.hideKeyboard
import support.bymason.kiosk.checkin.core.ui.onDestroy
import support.bymason.kiosk.checkin.core.ui.showKeyboard
import support.bymason.kiosk.checkin.databinding.HostFinderFragmentBinding

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
        binding.search.requestFocus()
        binding.search.showKeyboard()

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
        updateEnabledStatus(binding.root, !state.isSelectingHost)

        adapter.submitList(state.hosts)
    }

    private fun updateEnabledStatus(group: ViewGroup, enabled: Boolean) {
        for (child in group.children) {
            if (child is ViewGroup) updateEnabledStatus(child, enabled)
            child.isEnabled = enabled
        }
    }
}
