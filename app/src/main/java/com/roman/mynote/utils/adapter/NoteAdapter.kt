package com.roman.mynote.utils.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SpinnerAdapter
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintSet.Motion
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.roman.mynote.R
import com.roman.mynote.databinding.NoteCardBinding
import com.roman.mynote.utils.TimeManager
import com.romanuriel.core.room.model.NoteItem
import java.lang.IndexOutOfBoundsException
import java.util.Date

class NoteAdapter(
    private val onClickRoot: (NoteItem) -> Unit,
    private val onClickPin: (id: Long, pin: Boolean) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var listNoteItem = mutableListOf<NoteItem>()

    inner class NoteViewHolder(val binding: NoteCardBinding) : ViewHolder(binding.root) {
        fun build(noteItems: NoteItem) {
            binding.textViewTitle.text = noteItems.title
            binding.textNotes.text  = noteItems.content

            binding.notesContainer.setOnClickListener { onClickRoot(noteItems) }
            val date = Date(noteItems.dataCreate!!)

            val time = TimeManager(binding.root.context).getTimeAgo(date)
            Log.d("TAG-ITEM-TIME",time)
            Log.d("TAG-ITEM-DATE",time)

            binding.textDate.text = time
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

class SwipeToLeftCallback(
    private val adapter: RecyclerView.Adapter<*>,
    private val noteCallback: (NoteItem) -> Unit
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        target: ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val noteItem = getItemAtPosition(position)


    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        val motionLayout = (viewHolder.itemView as? MotionLayout)
        val viewX  = viewHolder.itemView.x
        val width = recyclerView.width

        val progress = if(viewX < 0){
            0f
        }else{
            1f
        }
        motionLayout?.progress = progress
    }

    private fun getItemAtPosition(position: Int): NoteItem{
        val itemCount = adapter.itemCount
        if(position in 0 until itemCount){
            return (adapter as NoteAdapter).getItem(position)
        }
        throw IndexOutOfBoundsException("Invalid position $position")
    }


}
