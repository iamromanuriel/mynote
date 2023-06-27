package com.roman.mynote.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roman.mynote.R
import com.roman.mynote.data.database.entity.Note

class NoteAdapter(val callback: (Note) -> Unit, val onLongClick:(Note) -> Unit) : RecyclerView.Adapter<NoteViewHolder>() {
    private var listNote = mutableListOf<Note>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NoteViewHolder(layoutInflater.inflate(R.layout.note_card, parent, false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = listNote[position]
        holder.render(item,callback, onLongClick)
    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    fun setListNote(list: List<Note>){
        this.listNote = list.toMutableList()
        notifyDataSetChanged()
    }
}