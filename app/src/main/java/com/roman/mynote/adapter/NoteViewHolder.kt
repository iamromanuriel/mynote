package com.roman.mynote.adapter

import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.animation.AlphaAnimation
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.roman.mynote.R
import com.roman.mynote.data.model.Note
import com.roman.mynote.databinding.BottomSheetDialogBinding
import com.roman.mynote.databinding.NoteCardBinding
import com.roman.mynote.ui.NoteActivity

class NoteViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val bindingCard = NoteCardBinding.bind(view)


    fun render(note: Note){
        with(bindingCard){
            textviewtitle.text = note.title 
            textNotes.text = note.note
            textDate.text = note.date

        }
    }
}