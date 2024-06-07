@file:Suppress("DUPLICATE_LABEL_IN_WHEN")

package com.romanuriel.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.color.MaterialColors
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

enum class ToastLength(val length: Int) {
    SHORT(Toast.LENGTH_SHORT),
    LONG(Toast.LENGTH_LONG)
}

enum class SnackBarLength(val length: Int){
    SHORT(200),
    MEDIUM(400),
    LONG(600)
}

fun Fragment.toast(message: Any, length: ToastLength = ToastLength.SHORT) {
    when(message){
        is String ->{  }
        is Int ->{ message.toString() }
        is @receiver:StringRes Int -> { message.toString() }
    }
    Toast.makeText(requireContext(), message.toString(), length.length).show()
}

@ColorInt
fun Context.getColorFromAttr(
    @AttrRes attrColor: Int
): Int {
    val typedArray = theme.obtainStyledAttributes(intArrayOf(attrColor))
    val textColor = typedArray.getColor(0, 0)
    typedArray.recycle()
    return textColor
}

@SuppressLint("PrivateResource")
fun View.snackBar(message: String, duration: SnackBarLength) = Snackbar.make(
    this, message, duration.length
).apply {
    this.setBackgroundTint(
        MaterialColors.getColor(
            this.context, com.google.android.material.R.attr.colorSurface, Color.WHITE
        )
    )
    this.setTextColor(ContextCompat.getColor(this@snackBar.context, com.google.android.material.R.color.m3_ref_palette_black))
}.show()

@SuppressLint("PrivateResource")
fun View.snackBar(
    message: String, duration: Int, actionName: String?, action: (() -> Unit)?
) = Snackbar.make(
    this, message, duration
).apply {
    this.setBackgroundTint(
        MaterialColors.getColor(
            this.context, com.google.android.material.R.attr.colorSurface, Color.WHITE
        )
    )
    this.setTextColor(ContextCompat.getColor(this@snackBar.context, com.google.android.material.R.color.m3_ref_palette_black))
    action?.let { action ->
        this.setActionTextColor(ContextCompat.getColor(this@snackBar.context, com.google.android.material.R.color.m3_ref_palette_dynamic_primary70))
        setAction(actionName) {
            action.invoke()
        }
    }
}.show()

fun Fragment.dialog(@StringRes resTitle: Int, message: String, cancel: () -> Unit, ok: () -> Unit) = this.apply {
    MaterialAlertDialogBuilder(requireContext())
        .setIcon(R.drawable.ic_warning)
        .setTitle(resources.getString(resTitle))
        .setMessage(message)
        .setNegativeButton(resources.getString(android.R.string.cancel)){ dialog, _ ->
            cancel()
            dialog.dismiss()
        }
        .setPositiveButton(resources.getString(android.R.string.ok)){ dialog, _ ->
            ok()
            dialog.dismiss()
        }
        .show()
}

fun Fragment.dialogTimePickerBasic( onPositive: (hour: Int, minute: Int)-> Unit, onNegative: () -> Unit ) = this.apply{
    val dialog = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_12H)
        .setHour(12)
        .setMinute(10)
        .setTitleText("Select time")
        .build()
    dialog.dialog?.setCanceledOnTouchOutside(false)
    dialog.addOnPositiveButtonClickListener { onPositive(dialog.hour, dialog.minute) }
    dialog.addOnNegativeButtonClickListener { onNegative() }
    showDialog(dialog)
}

fun Fragment.showDialog(dialog: DialogFragment) = this.apply {
    activity.let { dialog.show(it!!.supportFragmentManager, dialog.tag) }
}