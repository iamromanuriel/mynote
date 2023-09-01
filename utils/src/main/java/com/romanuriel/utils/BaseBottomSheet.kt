package com.romanuriel.utils

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.LayoutRes
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

@Suppress("UNREACHABLE_CODE")
open class BaseBottomSheet(@LayoutRes val layout : Int) : BottomSheetDialogFragment(layout) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
        BottomSheetDialog(requireContext(), theme)
    }
}