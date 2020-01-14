package support.bymason.kiosk.checkin.feature.identity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import support.bymason.kiosk.checkin.core.model.GuestFieldType
import support.bymason.kiosk.checkin.databinding.IdentityFragmentItemTypeCompanyBinding
import support.bymason.kiosk.checkin.databinding.IdentityFragmentItemTypeEmailBinding
import support.bymason.kiosk.checkin.databinding.IdentityFragmentItemTypeNameBinding
import support.bymason.kiosk.checkin.feature.identity.holders.CompanyIdentityViewHolder
import support.bymason.kiosk.checkin.feature.identity.holders.EmailIdentityViewHolder
import support.bymason.kiosk.checkin.feature.identity.holders.IdentityViewHolderBase
import support.bymason.kiosk.checkin.feature.identity.holders.NameIdentityViewHolder

class IdentityAdapter(
        private val vm: IdentityViewModel
) : ListAdapter<FieldState, IdentityViewHolderBase>(differ) {
    override fun getItemViewType(position: Int) = getItem(position).field.type.value

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ) = when (GuestFieldType.from(viewType)) {
        GuestFieldType.NAME -> NameIdentityViewHolder(
                vm,
                IdentityFragmentItemTypeNameBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false)
        )
        GuestFieldType.EMAIL -> EmailIdentityViewHolder(
                vm,
                IdentityFragmentItemTypeEmailBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false)
        )
        GuestFieldType.COMPANY -> CompanyIdentityViewHolder(
                vm,
                IdentityFragmentItemTypeCompanyBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: IdentityViewHolderBase, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object {
        val differ = object : DiffUtil.ItemCallback<FieldState>() {
            override fun areItemsTheSame(
                    oldItem: FieldState,
                    newItem: FieldState
            ) = oldItem.field.id == newItem.field.id

            override fun areContentsTheSame(
                    oldItem: FieldState,
                    newItem: FieldState
            ) = oldItem.field == newItem.field && oldItem.showError == newItem.showError
        }
    }
}
