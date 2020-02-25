package support.bymason.kiosk.checkin

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.FragmentFactory
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.nongmsauth.FirebaseAuthCompat
import support.bymason.kiosk.checkin.core.data.CheckInApi
import support.bymason.kiosk.checkin.core.data.DefaultCheckInApi
import support.bymason.kiosk.checkin.core.data.DefaultDispatcherProvider
import support.bymason.kiosk.checkin.core.data.DispatcherProvider
import support.bymason.kiosk.checkin.core.ui.ActivityBase
import support.bymason.kiosk.checkin.core.ui.AndroidBug5497Workaround
import support.bymason.kiosk.checkin.databinding.HomeActivityBinding
import support.bymason.kiosk.checkin.feature.hostfinder.HostFinderFragment
import support.bymason.kiosk.checkin.feature.identity.IdentityFragment
import support.bymason.kiosk.checkin.feature.nda.NdaFragment
import support.bymason.kiosk.checkin.feature.report.ReportFragment
import support.bymason.kiosk.checkin.feature.signin.SignInFragment
import support.bymason.kiosk.checkin.feature.welcome.WelcomeFragment

class HomeActivity : ActivityBase() {
    private val controller by lazy { findNavController(R.id.content) }
    private val configuration by lazy {
        AppBarConfiguration(setOf(R.id.welcome, R.id.login, R.id.identity, R.id.report))
    }

    private val fullScreenKeyboardAdjustResizeHack by lazy {
        AndroidBug5497Workaround(this)
    }

    init {
        supportFragmentManager.fragmentFactory = Factory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.MasonKiosk_NoActionBar)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)

        val binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(controller, configuration)
    }

    override fun onResume() {
        super.onResume()
        fullScreenKeyboardAdjustResizeHack.addListener()
    }

    override fun onPause() {
        super.onPause()
        fullScreenKeyboardAdjustResizeHack.removeListener()
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
