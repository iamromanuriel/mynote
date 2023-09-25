@file:Suppress("UNREACHABLE_CODE")

package com.roman.mynote.utils

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.google.android.material.materialswitch.MaterialSwitch
import com.roman.mynote.R
import com.roman.mynote.databinding.BarActionLayoutBinding

data class ToolbarModel(
    @StringRes
    val title: Int = R.string.app_name,
    val titleString: String? = null,
    @DrawableRes
    val icon: Int = com.romanuriel.utils.R.drawable.ic_arrow_back,
    val action:() -> Unit,
    @DrawableRes
    val endIcon: Int? = null,
    val endAction: () -> Unit = {},
    @StringRes
    val switchTitle: Int? = null,
    val switchAction: MaterialSwitch.() -> Unit = {},
    val switchOnClick:(Boolean) -> Unit = {}
)


fun BarActionLayoutBinding.set(toolbarModel: ToolbarModel) = this.apply {
    if(toolbarModel.titleString != null) textViewTitle.text = toolbarModel.titleString
    else textViewTitle.text = root.context.getText(toolbarModel.title)
    materialButtonClose.apply {
        setIconResource(toolbarModel.icon)
        setOnClickListener { toolbarModel.action() }
    }
    return@apply
    toolbarModel.endIcon?.let {
        materialButtonEnd.apply {
            visibility = View.VISIBLE
            setIconResource(it)
            setOnClickListener { toolbarModel.endAction() }
        }
    }
}