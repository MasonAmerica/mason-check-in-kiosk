package com.bymason.kiosk.checkin

import android.content.Context
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.NavHostFragment
import com.bymason.kiosk.checkin.feature.hostfinder.DefaultHostRepository
import com.bymason.kiosk.checkin.feature.hostfinder.HostFinderFragment
import com.bymason.kiosk.checkin.feature.hostfinder.HostRepository
import com.bymason.kiosk.checkin.feature.identity.DefaultIdentityRepository
import com.bymason.kiosk.checkin.feature.identity.IdentityFragment
import com.bymason.kiosk.checkin.feature.identity.IdentityRepository
import com.bymason.kiosk.checkin.feature.nda.DefaultNdaRepository
import com.bymason.kiosk.checkin.feature.nda.NdaFragment
import com.bymason.kiosk.checkin.feature.nda.NdaRepository
import com.bymason.kiosk.checkin.feature.signin.SignInFragment
import com.bymason.kiosk.checkin.feature.welcome.DefaultWelcomeRepository
import com.bymason.kiosk.checkin.feature.welcome.WelcomeFragment
import com.bymason.kiosk.checkin.feature.welcome.WelcomeRepository
import com.google.firebase.nongmsauth.FirebaseAuthCompat

class CheckInNavHostFragment : NavHostFragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        childFragmentManager.fragmentFactory = Factory()
    }

    class Factory(
            private val auth: FirebaseAuthCompat = FirebaseAuthCompat.getInstance(),
            private val welcomeRepository: WelcomeRepository = DefaultWelcomeRepository(),
            private val identityRepository: IdentityRepository = DefaultIdentityRepository(),
            private val hostRepository: HostRepository = DefaultHostRepository(),
            private val ndaRepository: NdaRepository = DefaultNdaRepository()
    ) : FragmentFactory() {
        override fun instantiate(
                classLoader: ClassLoader,
                className: String
        ) = when (loadFragmentClass(classLoader, className)) {
            SignInFragment::class.java -> SignInFragment(auth)
            WelcomeFragment::class.java -> WelcomeFragment(auth, welcomeRepository)
            IdentityFragment::class.java -> IdentityFragment(identityRepository)
            HostFinderFragment::class.java -> HostFinderFragment(hostRepository)
            NdaFragment::class.java -> NdaFragment(ndaRepository)
            else -> super.instantiate(classLoader, className)
        }
    }
}
