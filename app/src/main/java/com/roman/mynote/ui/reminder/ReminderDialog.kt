package com.roman.mynote.ui.reminder

import android.os.Bundle
import android.provider.ContactsContract.RawContacts.Data
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentNewReminderBinding
import com.romanuriel.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class ReminderDialog: BottomSheetDialogFragment(R.layout.fragment_new_reminder) {
    private val binding: FragmentNewReminderBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}