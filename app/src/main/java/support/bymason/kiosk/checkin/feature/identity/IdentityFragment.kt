package support.bymason.kiosk.checkin.feature.identity

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.EditText
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.coroutines.flow.collect
import support.bymason.kiosk.checkin.R
import support.bymason.kiosk.checkin.core.data.CheckInApi
import support.bymason.kiosk.checkin.core.data.DispatcherProvider
import support.bymason.kiosk.checkin.core.ui.FragmentBase
import support.bymason.kiosk.checkin.core.ui.LifecycleAwareLazy
import support.bymason.kiosk.checkin.core.ui.doOnImeDone
import support.bymason.kiosk.checkin.core.ui.onDestroy
import support.bymason.kiosk.checkin.core.ui.showKeyboard
import support.bymason.kiosk.checkin.databinding.IdentityFragmentBinding

class IdentityFragment(
        dispatchers: DispatcherProvider,
        api: CheckInApi
) : FragmentBase(R.layout.identity_fragment) {
    private val vm by viewModels<IdentityViewModel> {
        IdentityViewModel.Factory(dispatchers, DefaultIdentityRepository(dispatchers, api))
    }

    private val binding by LifecycleAwareLazy {
        IdentityFragmentBinding.bind(requireView())
    } onDestroy {
        fields.viewTreeObserver.removeOnGlobalLayoutListener(installer)
    }
    private val progress: View? by lazy { requireActivity().findViewById<View>(R.id.progress) }
    private var restartMenuItem: MenuItem? = null
    private val installer = KeyboardInstaller()

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            vm.actions.collect { onActionRequested(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.next.setOnClickListener { vm.onContinue() }

        val adapter = IdentityAdapter(vm)
        binding.fields.adapter = adapter
        binding.fields.itemAnimator = null
        binding.fields.viewTreeObserver.addOnGlobalLayoutListener(installer)

        vm.state.observe(viewLifecycleOwner) {
            onViewStateChanged(it, adapter)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.check_in_options, menu)
        restartMenuItem = menu.findItem(R.id.action_restart)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_restart -> vm.onRestartRequested()
            else -> return false
        }

        return true
    }

    private fun onActionRequested(action: IdentityViewModel.Action) {
        when (action) {
            is IdentityViewModel.Action.Navigate -> findNavController().navigate(action.directions)
        }
    }

    private fun onViewStateChanged(state: IdentityViewModel.State, adapter: IdentityAdapter) {
        progress?.isVisible = state.isLoading
        updateEnabledStatus(binding.root, state.areViewEnabled)
        binding.next.isEnabled = state.areViewEnabled && state.isContinueButtonEnabled
        binding.next.isVisible = state.areButtonsVisible
        restartMenuItem?.isVisible = state.areButtonsVisible

        Glide.with(this)
                .load(state.companyLogoUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.logo)

        adapter.submitList(state.fieldStates)
    }

    private fun updateEnabledStatus(group: ViewGroup, enabled: Boolean) {
        for (child in group.children) {
            if (child is ViewGroup) updateEnabledStatus(child, enabled)
            child.isEnabled = enabled
        }
    }

    inner class KeyboardInstaller : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (view == null) return

            val lm = binding.fields.layoutManager ?: return
            val first = lm.getChildAt(0) ?: return
            val last = lm.getChildAt(lm.childCount - 1) ?: return
            val firstInput = (first as? ViewGroup)?.findEditText()
            val lastInput = (last as? ViewGroup)?.findEditText()

            firstInput?.requestFocus()
            firstInput?.showKeyboard()

            lastInput?.doOnImeDone { vm.onContinue() }

            binding.fields.viewTreeObserver.removeOnGlobalLayoutListener(this)
        }

        private fun ViewGroup.findEditText(): EditText? {
            for (child in children) {
                if (child is ViewGroup) {
                    val inner = child.findEditText()
                    if (inner != null) return inner
                } else if (child is EditText) {
                    return child
                }
            }
            return null
        }
    }
}
