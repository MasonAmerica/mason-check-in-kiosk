package support.bymason.kiosk.checkin.feature.hostfinder

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import support.bymason.kiosk.checkin.R
import support.bymason.kiosk.checkin.core.model.Host
import support.bymason.kiosk.checkin.databinding.HostFinderFragmentItemBinding

class HostViewHolder(
        private val fragment: Fragment,
        private val vm: HostFinderViewModel,
        private val binding: HostFinderFragmentItemBinding
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    private lateinit var host: Host

    init {
        binding.root.setOnClickListener(this)
    }

    fun bind(host: Host) {
        this.host = host

        binding.name.text = host.name
        Glide.with(fragment)
                .load(host.photoUrl)
                .placeholder(R.drawable.ic_person_24dp)
                .circleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.profile)
    }

    override fun onClick(v: View) {
        vm.onFound(host)
    }
}
