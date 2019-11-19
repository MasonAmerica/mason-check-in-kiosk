package com.bymason.kiosk.checkin

import android.content.Context
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.NavHostFragment
import com.bymason.kiosk.checkin.feature.masonitefinder.DefaultEmployeeRepository
import com.bymason.kiosk.checkin.feature.masonitefinder.EmployeeRepository
import com.bymason.kiosk.checkin.feature.masonitefinder.MasoniteFinderFragment

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
            MasoniteFinderFragment::class.java -> MasoniteFinderFragment(repository)
            else -> super.instantiate(classLoader, className)
        }
    }
}
