package com.bymason.kiosk.checkin.feature.employeefinder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bymason.kiosk.checkin.core.model.Employee
import com.bymason.kiosk.checkin.databinding.EmployeeFinderFragmentItemBinding

class EmployeeAdapter(
        private val fragment: Fragment,
        private val vm: EmployeeFinderViewModel
) : ListAdapter<Employee, EmployeeViewHolder>(differ) {
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ) = EmployeeViewHolder(
            fragment,
            vm,
            EmployeeFinderFragmentItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object {
        val differ = object : DiffUtil.ItemCallback<Employee>() {
            override fun areItemsTheSame(
                    oldItem: Employee,
                    newItem: Employee
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                    oldItem: Employee,
                    newItem: Employee
            ) = oldItem == newItem
        }
    }
}
