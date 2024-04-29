package com.example.studentportal.common.ui

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.commit
import com.example.studentportal.common.ui.fragment.BaseDialogFragment
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.home.ui.fragment.HomeFragment

/**
 * Pop backstack to this fragment
 */
fun FragmentManager.popBackStackToFragment(fragment: BaseFragment<*>) {
    if (fragment is HomeFragment) {
        popBackStack(null, POP_BACK_STACK_INCLUSIVE)
    } else {
        popBackStack(fragment.fragmentTag, 0)
    }
}

fun FragmentManager.showBaseFragment(
    fragment: BaseFragment<*>,
    addToBackStack: Boolean,
    @IdRes containerId: Int
) {
    commit {
        setReorderingAllowed(true)
        replace(containerId, fragment, fragment.fragmentTag)
        if (addToBackStack) {
            addToBackStack(fragment.fragmentTag)
        }
    }
}

fun FragmentManager.showBaseDialogFragment(
    fragment: BaseDialogFragment<*>
) {
    val transaction = beginTransaction()
    transaction.addToBackStack(fragment.fragmentTag)
    fragment.show(transaction, fragment.fragmentTag)
}
