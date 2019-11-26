package com.bymason.kiosk.checkin.feature.nda

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bymason.kiosk.checkin.R
import com.bymason.kiosk.checkin.core.ui.FragmentBase
import com.bymason.kiosk.checkin.core.ui.LifecycleAwareLazy
import com.bymason.kiosk.checkin.databinding.NdaFragmentBinding

class NdaFragment(
        repository: NdaRepository
) : FragmentBase(R.layout.nda_fragment) {
    private val args by navArgs<NdaFragmentArgs>()

    private val vm by viewModels<NdaViewModel> { NdaViewModel.Factory(repository) }
    private val binding by LifecycleAwareLazy { NdaFragmentBinding.bind(requireView()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.finish.setOnClickListener { vm.finish(args.employee, args.guest) }
    }
}
