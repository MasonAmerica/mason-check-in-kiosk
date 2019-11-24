package com.bymason.kiosk.checkin.feature.employeefinder

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.bymason.kiosk.checkin.R
import com.bymason.kiosk.checkin.core.ui.FragmentBase
import com.bymason.kiosk.checkin.core.ui.LifecycleAwareLazy
import com.bymason.kiosk.checkin.databinding.EmployeeFinderFragmentBinding

class EmployeeFinderFragment(
        repository: EmployeeRepository
) : FragmentBase(R.layout.employee_finder_fragment) {
    private val args by navArgs<EmployeeFinderFragmentArgs>()

    private val vm by viewModels<EmployeeFinderViewModel> {
        EmployeeFinderViewModel.Factory(repository)
    }
    private val binding by LifecycleAwareLazy { EmployeeFinderFragmentBinding.bind(requireView()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.search.doAfterTextChanged {
            vm.find(it?.toString())
        }

        val adapter = EmployeeAdapter(this, args.guest)
        binding.employees.adapter = adapter
        vm.employees.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.noEmployeesHint.isVisible = it.isEmpty()
        }
    }
}
