package com.bymason.kiosk.checkin.feature.identity.holders

import com.bymason.kiosk.checkin.databinding.IdentityFragmentItemTypeCompanyBinding
import com.bymason.kiosk.checkin.feature.identity.IdentityViewModel

class CompanyIdentityViewHolder(
        vm: IdentityViewModel,
        binding: IdentityFragmentItemTypeCompanyBinding
) : StringIdentityViewHolderBase(vm, binding.root, binding.root)
