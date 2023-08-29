package com.romanuriel.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.transition.MaterialSharedAxis

val FragmentManager.currentNavigationFragment: Fragment?
    get() = primaryNavigationFragment?.childFragmentManager?.fragments?.first()

enum class Axis(val value: Int){
    x(MaterialSharedAxis.X),
    Y(MaterialSharedAxis.Y),
    Z(MaterialSharedAxis.Z)
}

fun Fragment.exitTransition(axis: Axis = Axis.Y) =
    this.run {
        exitTransition = MaterialSharedAxis(axis.value, true)
        reenterTransition = MaterialSharedAxis(axis.value, false)
    }

fun Fragment.enterTransitionS(axis: Axis = Axis.Y) =
    this.run {
        enterTransition = MaterialSharedAxis(axis.value, true)
        returnTransition = MaterialSharedAxis(axis.value, false)
    }