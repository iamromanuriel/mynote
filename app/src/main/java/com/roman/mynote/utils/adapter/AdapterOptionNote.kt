package com.roman.mynote.utils.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roman.mynote.R
import com.roman.mynote.databinding.LayoutItemOptionBinding
import com.roman.mynote.utils.model.OptionNoteModel

class AdapterOptionNote(
    val actionClick: () -> Unit
): RecyclerView.Adapter<AdapterOptionNote.OptionNoteViewHolder>() {

    private var list = listOf(
        OptionNoteModel("Delete","",R.drawable.ic_delete, androidx.transition.R.color.error_color_material_light ),
        OptionNoteModel(title = "Pin", icon = R.drawable.ic_pin),
        OptionNoteModel(title = "Save", icon = R.drawable.ic_save),
        OptionNoteModel(title = "Action", icon = R.drawable.ic_audio))
    inner class OptionNoteViewHolder(val binding: LayoutItemOptionBinding) : RecyclerView.ViewHolder(binding.root){
        fun build(optionNoteModel: OptionNoteModel){
            //if(optionNoteModel.icon != null) binding.imageReference.setImageDrawable(binding.root.resources.getDrawable(optionNoteModel.icon))

            binding.textTitle.text = optionNoteModel.title
            binding.textDescription.text = optionNoteModel.description

            binding.root.setOnClickListener { actionClick() }

            Log.d("TAG-DATA-OPTION","")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionNoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_option, parent, false)
        return OptionNoteViewHolder(LayoutItemOptionBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: OptionNoteViewHolder, position: Int) {
        val item = list[position]
        holder.build(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<OptionNoteModel>){
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }
}