package com.bymason.kiosk.checkin.feature.identity.holders

import com.bymason.kiosk.checkin.databinding.IdentityFragmentItemTypeNameBinding
import com.bymason.kiosk.checkin.feature.identity.IdentityViewModel

class NameIdentityViewHolder(
        vm: IdentityViewModel,
        binding: IdentityFragmentItemTypeNameBinding
) : StringIdentityViewHolderBase(vm, binding.root, binding.root)
