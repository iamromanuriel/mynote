package com.roman.mynote.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.roman.mynote.R

fun FloatingActionButton.setupExpandableBehavior(itemOptions: List<View>, ) {
    val context: Context = this.context

    val fromBottomFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.from_button_fab)
    }
    val toBottomFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.to_bottom_fab)
    }
    val rotateClockWiseFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.rotate_clock_wise)
    }
    val rotateAntiClockWiseFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.rotate_anti_clock_wise)
    }
    val fromBottomBgAnim: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.from_button_anim)
    }
    val toBottomBgAnim: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.to_bottom_anim)
    }

    var isExpanded = false

    val expandSecondaryFab = {
        for (fab in itemOptions) {
            fab.isVisible = true
        }
    }

    val collapseSecondaryFab = {
        for (fab in itemOptions) {
            fab.isVisible = false
        }
    }

    fun shrinkFab() {
        startAnimation(rotateAntiClockWiseFabAnim)
    }

    fun expandFab() {
        startAnimation(rotateClockWiseFabAnim)

    }

    fun toggleSecondaryFab() {
        isExpanded = !isExpanded
        if (isExpanded) {
            expandFab()
            expandSecondaryFab()
        } else {
            shrinkFab()
            collapseSecondaryFab()
        }
    }

    setOnClickListener { toggleSecondaryFab() }
}

