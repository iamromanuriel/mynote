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

class NoteAdapter(
    private val onClickRoot: (NoteItem) -> Unit,
    private val onClickPin: (id: Long, pin: Boolean) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var listNoteItem = mutableListOf<NoteItem>()

    inner class NoteViewHolder(val binding: NoteCardBinding) : ViewHolder(binding.root) {
        fun build(noteItems: NoteItem) {
            binding.apply {
                textViewTitle.text = noteItems.title
                textDate.text = noteItems.dataCreate.toString()

                imagePind.visibility = View.VISIBLE
                binding.notesContainer.setOnClickListener {
                    onClickRoot(noteItems)
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
}
