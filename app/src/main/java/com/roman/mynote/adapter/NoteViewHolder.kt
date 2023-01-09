package com.roman.mynote.adapter

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.roman.mynote.data.model.Note
import com.roman.mynote.databinding.NoteCardBinding

class NoteViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val bindingCard = NoteCardBinding.bind(view)

    fun render(note: Note,callback: (Note) -> Unit){
        bindingCard.textviewtitle.text = note.title
        bindingCard.textNotes.text = note.note
        bindingCard.textDate.text = note.date
        bindingCard.root.setOnClickListener { callback(note) }
    }
}