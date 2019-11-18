package com.bymason.kiosk.checkin.feature.masonitefinder

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bymason.kiosk.checkin.R
import com.bymason.kiosk.checkin.core.model.Employee
import com.bymason.kiosk.checkin.databinding.MasoniteFinderFragmentItemBinding

class EmployeeViewHolder(
        private val fragment: Fragment,
        private val binding: MasoniteFinderFragmentItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(employee: Employee) {
        binding.name.text = employee.name
        Glide.with(fragment)
                .load(employee.photoUrl)
                .placeholder(R.drawable.ic_person_24dp)
                .circleCrop()
                .into(binding.profile)
    }
}
