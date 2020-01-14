package support.bymason.kiosk.checkin.feature.identity.holders

import support.bymason.kiosk.checkin.databinding.IdentityFragmentItemTypeNameBinding
import support.bymason.kiosk.checkin.feature.identity.IdentityViewModel

class NameIdentityViewHolder(
        vm: IdentityViewModel,
        binding: IdentityFragmentItemTypeNameBinding
) : StringIdentityViewHolderBase(vm, binding.root, binding.root)
