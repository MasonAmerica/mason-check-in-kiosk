package com.bymason.kiosk.checkin

import android.content.Context
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.NavHostFragment
import com.bymason.kiosk.checkin.core.data.CheckInApi
import com.bymason.kiosk.checkin.core.data.DefaultCheckInApi
import com.bymason.kiosk.checkin.core.data.DefaultDispatcherProvider
import com.bymason.kiosk.checkin.core.data.DispatcherProvider
import com.bymason.kiosk.checkin.feature.hostfinder.HostFinderFragment
import com.bymason.kiosk.checkin.feature.identity.IdentityFragment
import com.bymason.kiosk.checkin.feature.nda.NdaFragment
import com.bymason.kiosk.checkin.feature.report.ReportFragment
import com.bymason.kiosk.checkin.feature.signin.SignInFragment
import com.bymason.kiosk.checkin.feature.welcome.WelcomeFragment
import com.google.firebase.nongmsauth.FirebaseAuthCompat

class CheckInNavHostFragment : NavHostFragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        childFragmentManager.fragmentFactory = Factory()
    }

    class Factory(
            private val dispatchers: DispatcherProvider = DefaultDispatcherProvider,
            private val auth: FirebaseAuthCompat = FirebaseAuthCompat.getInstance(),
            private val api: CheckInApi = DefaultCheckInApi(dispatchers)
    ) : FragmentFactory() {
        override fun instantiate(
                classLoader: ClassLoader,
                className: String
        ) = when (loadFragmentClass(classLoader, className)) {
            SignInFragment::class.java -> SignInFragment(auth)
            WelcomeFragment::class.java -> WelcomeFragment(auth)
            IdentityFragment::class.java -> IdentityFragment(dispatchers, api)
            HostFinderFragment::class.java -> HostFinderFragment(dispatchers, api)
            NdaFragment::class.java -> NdaFragment(api)
            ReportFragment::class.java -> ReportFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}
