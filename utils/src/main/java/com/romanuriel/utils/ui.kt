package com.romanuriel.utils

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar

enum class ToastLength(val length: Int) {
    SHORT(Toast.LENGTH_SHORT),
    LONG(Toast.LENGTH_LONG)
}

fun Fragment.toast(message: String, length: ToastLength = ToastLength.SHORT) =
    Toast.makeText(requireContext(), message, length.length).show()

@ColorInt
fun Context.getColorFromAttr(
    @AttrRes attrColor: Int
): Int {
    val typedArray = theme.obtainStyledAttributes(intArrayOf(attrColor))
    val textColor = typedArray.getColor(0, 0)
    typedArray.recycle()
    return textColor
}

fun View.showSnackBar(message: String, duration: Int) = Snackbar.make(
    this, message, duration
).apply {
    this.setBackgroundTint(
        MaterialColors.getColor(
            this.context, com.google.android.material.R.attr.colorSurface, Color.WHITE
        )
    )
    this.setTextColor(ContextCompat.getColor(this@showSnackBar.context, com.google.android.material.R.color.m3_ref_palette_black))
}.show()

fun View.showSnackBar(
    message: String, duration: Int, actionName: String?, action: (() -> Unit)?
) = Snackbar.make(
    this, message, duration
).apply {
    this.setBackgroundTint(
        MaterialColors.getColor(
            this.context, com.google.android.material.R.attr.colorSurface, Color.WHITE
        )
    )
    this.setTextColor(ContextCompat.getColor(this@showSnackBar.context, com.google.android.material.R.color.m3_ref_palette_black))
    action?.let { action ->
        this.setActionTextColor(ContextCompat.getColor(this@showSnackBar.context, com.google.android.material.R.color.m3_ref_palette_dynamic_primary70))
        setAction(actionName) {
            action.invoke()
        }
    }
}.show()

