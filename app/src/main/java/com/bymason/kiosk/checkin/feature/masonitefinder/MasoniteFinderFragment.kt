package com.bymason.kiosk.checkin.feature.masonitefinder

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
import com.bymason.kiosk.checkin.databinding.MasoniteFinderFragmentBinding

class MasoniteFinderFragment(
        repository: EmployeeRepository
) : FragmentBase(R.layout.masonite_finder_fragment) {
    private val args by navArgs<MasoniteFinderFragmentArgs>()

    private val vm by viewModels<MasoniteFinderViewModel> {
        MasoniteFinderViewModel.Factory(repository)
    }
    private val binding by LifecycleAwareLazy { MasoniteFinderFragmentBinding.bind(requireView()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.search.doAfterTextChanged {
            vm.find(it?.toString())
        }

        val adapter = EmployeeAdapter(this, args.guest)
        binding.masonites.adapter = adapter
        vm.masonites.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.noEmployeesHint.isVisible = it.isEmpty()
        }
    }
}
