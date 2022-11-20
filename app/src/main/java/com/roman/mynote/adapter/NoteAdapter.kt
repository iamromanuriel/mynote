package com.roman.mynote.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.roman.mynote.R
import com.roman.mynote.data.model.Note
import com.roman.mynote.databinding.BottomSheetDialogBinding
import com.roman.mynote.utils.BottonSheetDialogNote

class NoteAdapter(val listNote: List<Note>): RecyclerView.Adapter<NoteViewHolder>() {
    var onItemClick: ((Note) -> Unit)? =null
    var onNavClick: ((Note) ->Unit)?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NoteViewHolder(layoutInflater.inflate(R.layout.note_card, parent, false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = listNote[position]
        holder.render(item)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
            onNavClick?.invoke(item)

        }
    }

    override fun getItemCount(): Int = listNote.size


}