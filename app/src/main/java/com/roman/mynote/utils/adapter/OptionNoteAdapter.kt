package com.roman.mynote.utils.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roman.mynote.R
import com.roman.mynote.databinding.ItemLayoutOptionActionNoteBinding
import com.roman.mynote.databinding.LayoutButtomSheetDialogOptionBinding
import com.roman.mynote.utils.ItemOptionButtonSheet

class OptionNoteAdapter(private val onClick: () -> Unit) :
    RecyclerView.Adapter<OptionNoteAdapter.OptionNoteViewHolder>() {

    private var listOptionItem = mutableListOf<ItemOptionButtonSheet>()

    inner class OptionNoteViewHolder(private val binding: ItemLayoutOptionActionNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun build(item: ItemOptionButtonSheet) {
            binding.textTitleOption.text = binding.root.context.getString(item.title)
            if (item.icon != null) binding.iconOption.setIconResource(item.icon)
            binding.root.setOnClickListener{ onClick() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionNoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_buttom_sheet_dialog_option, parent, false)
        return OptionNoteViewHolder(ItemLayoutOptionActionNoteBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return listOptionItem.size
    }

    override fun onBindViewHolder(holder: OptionNoteViewHolder, position: Int) {
        val item = listOptionItem[position]
        holder.build(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<ItemOptionButtonSheet>) {
        this.listOptionItem = list.toMutableList()
        notifyDataSetChanged()
    }
}