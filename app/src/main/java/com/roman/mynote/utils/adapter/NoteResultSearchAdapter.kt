package com.roman.mynote.utils.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roman.mynote.R
import com.roman.mynote.databinding.LayoutResultSearchNoteBinding
import com.romanuriel.utils.ResultSearchNoteData


class NoteResultSearchAdapter(
    val onClick: (ResultSearchNoteData) -> Unit
) : RecyclerView.Adapter<NoteResultSearchAdapter.NoteResultSearchViewHolder>() {
    private var listResult = mutableListOf<ResultSearchNoteData>()

    inner class NoteResultSearchViewHolder(private val binding: LayoutResultSearchNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun build(resultSearchNoteData: ResultSearchNoteData) {
            binding.apply {
                textResult.text = resultSearchNoteData.content
                imageClose.visibility = resultSearchNoteData.searched!!
                imageClose.setOnClickListener {
                    onClick(resultSearchNoteData)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteResultSearchAdapter.NoteResultSearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_result_search_note, parent, false)
        return NoteResultSearchViewHolder(LayoutResultSearchNoteBinding.bind(view))
    }

    override fun onBindViewHolder(
        holder: NoteResultSearchAdapter.NoteResultSearchViewHolder,
        position: Int
    ) {
        val result = listResult[position]
        holder.build(result)
    }

    override fun getItemCount(): Int {
        return listResult.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<ResultSearchNoteData>) {
        this.listResult = list.toMutableList()
        notifyDataSetChanged()
    }

}