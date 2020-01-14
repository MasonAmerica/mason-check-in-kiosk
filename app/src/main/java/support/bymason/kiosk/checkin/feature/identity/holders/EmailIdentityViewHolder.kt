package support.bymason.kiosk.checkin.feature.identity.holders

import support.bymason.kiosk.checkin.databinding.IdentityFragmentItemTypeEmailBinding
import support.bymason.kiosk.checkin.feature.identity.IdentityViewModel

class EmailIdentityViewHolder(
        vm: IdentityViewModel,
        binding: IdentityFragmentItemTypeEmailBinding
) : StringIdentityViewHolderBase(vm, binding.root, binding.root)
