package com.bymason.kiosk.checkin.feature.employeefinder

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bymason.kiosk.checkin.R
import com.bymason.kiosk.checkin.core.ui.FragmentBase
import com.bymason.kiosk.checkin.core.ui.LifecycleAwareLazy
import com.bymason.kiosk.checkin.core.ui.hideKeyboard
import com.bymason.kiosk.checkin.core.ui.onDestroy
import com.bymason.kiosk.checkin.databinding.EmployeeFinderFragmentBinding
import kotlinx.coroutines.flow.collect

class EmployeeFinderFragment(
        repository: EmployeeRepository
) : FragmentBase(R.layout.employee_finder_fragment) {
    private val args by navArgs<EmployeeFinderFragmentArgs>()

    private val vm by viewModels<EmployeeFinderViewModel> {
        EmployeeFinderViewModel.Factory(repository)
    }
    private val binding by LifecycleAwareLazy {
        EmployeeFinderFragmentBinding.bind(requireView())
    } onDestroy {
        search.clearFocus()
        search.hideKeyboard()
    }
    private val progress by lazy { requireActivity().findViewById<View>(R.id.progress) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            vm.actions.collect { onActionRequested(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.search.doAfterTextChanged {
            vm.find(it?.toString())
        }

        val adapter = EmployeeAdapter(this, vm, args.guest)
        binding.employees.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            vm.viewActions.collect { onViewActionRequested(it) }
        }
        vm.state.observe(viewLifecycleOwner) {
            onViewStateChanged(it, adapter)
        }
    }

    private fun onActionRequested(action: EmployeeFinderViewModel.Action) {
        when (action) {
            is EmployeeFinderViewModel.Action.Navigate ->
                findNavController().navigate(action.directions)
        }
    }

    private fun onViewActionRequested(action: EmployeeFinderViewModel.ViewAction) {
        error("No view actions implemented")
    }

    private fun onViewStateChanged(state: EmployeeFinderViewModel.State, adapter: EmployeeAdapter) {
        progress.isVisible = state.isLoading
        binding.noEmployeesHint.isVisible = state.isSearchHintVisible
        adapter.submitList(state.employees)
    }
}
