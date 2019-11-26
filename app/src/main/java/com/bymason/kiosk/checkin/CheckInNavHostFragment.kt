package com.bymason.kiosk.checkin

import android.content.Context
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.NavHostFragment
import com.bymason.kiosk.checkin.feature.employeefinder.DefaultEmployeeRepository
import com.bymason.kiosk.checkin.feature.employeefinder.EmployeeFinderFragment
import com.bymason.kiosk.checkin.feature.employeefinder.EmployeeRepository
import com.bymason.kiosk.checkin.feature.nda.DefaultNdaRepository
import com.bymason.kiosk.checkin.feature.nda.NdaFragment
import com.bymason.kiosk.checkin.feature.nda.NdaRepository

class CheckInNavHostFragment : NavHostFragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        childFragmentManager.fragmentFactory = Factory()
    }

    class Factory(
            private val employeeRepository: EmployeeRepository = DefaultEmployeeRepository(),
            private val ndaRepository: NdaRepository = DefaultNdaRepository()
    ) : FragmentFactory() {
        override fun instantiate(
                classLoader: ClassLoader,
                className: String
        ) = when (loadFragmentClass(classLoader, className)) {
            EmployeeFinderFragment::class.java -> EmployeeFinderFragment(employeeRepository)
            NdaFragment::class.java -> NdaFragment(ndaRepository)
            else -> super.instantiate(classLoader, className)
        }
    }
}
