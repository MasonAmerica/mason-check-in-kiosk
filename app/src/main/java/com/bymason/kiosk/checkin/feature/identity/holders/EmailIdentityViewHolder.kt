package com.bymason.kiosk.checkin.feature.identity.holders

import com.bymason.kiosk.checkin.databinding.IdentityFragmentItemTypeEmailBinding
import com.bymason.kiosk.checkin.feature.identity.IdentityViewModel

class EmailIdentityViewHolder(
        vm: IdentityViewModel,
        binding: IdentityFragmentItemTypeEmailBinding
) : StringIdentityViewHolderBase(vm, binding.root, binding.root)
