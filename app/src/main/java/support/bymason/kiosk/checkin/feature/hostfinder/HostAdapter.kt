package support.bymason.kiosk.checkin.feature.hostfinder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import support.bymason.kiosk.checkin.core.model.Host
import support.bymason.kiosk.checkin.databinding.HostFinderFragmentItemBinding

class HostAdapter(
        private val fragment: Fragment,
        private val vm: HostFinderViewModel
) : ListAdapter<Host, HostViewHolder>(differ) {
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ) = HostViewHolder(
            fragment,
            vm,
            HostFinderFragmentItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: HostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object {
        val differ = object : DiffUtil.ItemCallback<Host>() {
            override fun areItemsTheSame(
                    oldItem: Host,
                    newItem: Host
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                    oldItem: Host,
                    newItem: Host
            ) = oldItem == newItem
        }
    }
}
