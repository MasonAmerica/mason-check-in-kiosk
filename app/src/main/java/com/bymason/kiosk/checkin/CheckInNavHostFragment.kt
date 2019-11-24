package com.bymason.kiosk.checkin

import android.content.Context
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.NavHostFragment
import com.bymason.kiosk.checkin.feature.employeefinder.DefaultEmployeeRepository
import com.bymason.kiosk.checkin.feature.employeefinder.EmployeeFinderFragment
import com.bymason.kiosk.checkin.feature.employeefinder.EmployeeRepository

class CheckInNavHostFragment : NavHostFragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        childFragmentManager.fragmentFactory = Factory()
    }

    class Factory(
            private val repository: EmployeeRepository = DefaultEmployeeRepository()
    ) : FragmentFactory() {
        override fun instantiate(
                classLoader: ClassLoader,
                className: String
        ) = when (loadFragmentClass(classLoader, className)) {
            EmployeeFinderFragment::class.java -> EmployeeFinderFragment(repository)
            else -> super.instantiate(classLoader, className)
        }
    }
}
