package com.bymason.kiosk.checkin

import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.bymason.kiosk.checkin.core.data.CheckInApi
import com.bymason.kiosk.checkin.core.data.DefaultCheckInApi
import com.bymason.kiosk.checkin.core.data.DefaultDispatcherProvider
import com.bymason.kiosk.checkin.core.data.DispatcherProvider
import com.bymason.kiosk.checkin.core.ui.ActivityBase
import com.bymason.kiosk.checkin.databinding.HomeActivityBinding
import com.bymason.kiosk.checkin.feature.hostfinder.HostFinderFragment
import com.bymason.kiosk.checkin.feature.identity.IdentityFragment
import com.bymason.kiosk.checkin.feature.nda.NdaFragment
import com.bymason.kiosk.checkin.feature.report.ReportFragment
import com.bymason.kiosk.checkin.feature.signin.SignInFragment
import com.bymason.kiosk.checkin.feature.welcome.WelcomeFragment
import com.google.firebase.nongmsauth.FirebaseAuthCompat

class HomeActivity : ActivityBase() {
    private val controller by lazy { findNavController(R.id.content) }
    private val configuration by lazy {
        AppBarConfiguration(setOf(R.id.welcome, R.id.login, R.id.identity, R.id.report))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.MasonKiosk_NoActionBar)
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = Factory()

        val binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(controller, configuration)
    }

    override fun onSupportNavigateUp() =
            controller.navigateUp(configuration) || super.onSupportNavigateUp()

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
