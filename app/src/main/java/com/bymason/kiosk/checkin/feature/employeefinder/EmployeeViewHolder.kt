package com.bymason.kiosk.checkin.feature.employeefinder

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bymason.kiosk.checkin.R
import com.bymason.kiosk.checkin.core.model.Employee
import com.bymason.kiosk.checkin.core.model.Guest
import com.bymason.kiosk.checkin.databinding.EmployeeFinderFragmentItemBinding

class EmployeeViewHolder(
        private val fragment: Fragment,
        private val vm: EmployeeFinderViewModel,
        private val guest: Guest,
        private val binding: EmployeeFinderFragmentItemBinding
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    private lateinit var employee: Employee

    init {
        binding.root.setOnClickListener(this)
    }

    fun bind(employee: Employee) {
        this.employee = employee

        binding.name.text = employee.name
        Glide.with(fragment)
                .load(employee.photoUrl)
                .placeholder(R.drawable.ic_person_24dp)
                .circleCrop()
                .into(binding.profile)
    }

    override fun onClick(v: View) {
        vm.onFound(employee, guest)
    }
}
