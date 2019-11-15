package com.bymason.kiosk.checkin.core.ui

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

abstract class ActivityBase : AppCompatActivity()

abstract class FragmentBase(@LayoutRes contentLayoutId: Int = 0) : Fragment(contentLayoutId)

abstract class DialogFragmentBase : DialogFragment()
