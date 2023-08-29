package com.romanuriel.utils

import androidx.fragment.app.Fragment
import com.romanuriel.utils.databinding.LayoutLoadingBinding

fun Fragment.showLoading(visibility: Int) {
    val binding = LayoutLoadingBinding.bind(view!!)
    binding.root.visibility = visibility
}