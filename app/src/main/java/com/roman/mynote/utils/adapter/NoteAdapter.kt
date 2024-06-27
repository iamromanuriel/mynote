package com.roman.mynote.utils.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.roman.mynote.R
import com.roman.mynote.databinding.NoteCardBinding
import com.romanuriel.core.room.model.NoteItem
import com.romanuriel.utils.loadImage

class NoteAdapter(
    val onClickRoot: (NoteItem) -> Unit,
    val onLogClick: (view: View, noteItem: NoteItem) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var listNoteItem = mutableListOf<NoteItem>()

    inner class NoteViewHolder(val binding: NoteCardBinding) : ViewHolder(binding.root) {
        fun build(noteItems: NoteItem) {
            when(noteItems.categoryId){
                1L -> {
                    binding.containerIcon.setBackgroundColor(binding.root.context.getColor(R.color.CustomColor1))
                    binding.imageRef.loadImage(R.drawable.publicalo)
                }
                2L -> { binding.imageRef.loadImage(R.drawable.onda_sonora) }
                3L -> { binding.imageRef.loadImage(R.drawable.veintiseis) }
            }
            binding.title.text = noteItems.title
            if(noteItems.pin == true) {
                binding.btnPin.visibility == View.VISIBLE
            }
            else {
                binding.btnPin.visibility == View.GONE
            }
            binding.root.setOnClickListener { onClickRoot(noteItems) }
            binding.root.let {
                it.setOnLongClickListener {
                    onLogClick(it, noteItems)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_card, parent, false)
        return NoteViewHolder(NoteCardBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return listNoteItem.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val noteItem = listNoteItem[position]
        holder.build(noteItem)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<NoteItem>) {
        this.listNoteItem = list.toMutableList()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): NoteItem{
        return listNoteItem[position]
    }
}