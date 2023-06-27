package com.roman.mynote.utils.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.roman.mynote.data.database.entity.Note
import com.roman.mynote.databinding.NoteCardBinding

class NoteViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val bindingCard = NoteCardBinding.bind(view)
    fun render(note: Note, callback: (Note) -> Unit, clickLog: (Note) -> Unit){
        bindingCard.textviewtitle.text = note.title
        bindingCard.textNotes.text = note.note
        bindingCard.textDate.text = note.dateCreate.toString()
        if(note.pinned){ bindingCard.imagePind.visibility = View.VISIBLE }
        else{bindingCard.imagePind.visibility = View.INVISIBLE}
        bindingCard.root.setOnClickListener { callback(note) }
        bindingCard.root.setOnLongClickListener{
            clickLog(note)
            true
        }
    }
}