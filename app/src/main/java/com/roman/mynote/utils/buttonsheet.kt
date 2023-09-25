package com.roman.mynote.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roman.mynote.databinding.LayoutButtomSheetDialogOptionBinding
import com.roman.mynote.utils.adapter.OptionNoteAdapter


data class ItemOptionButtonSheet(
    val id: Long,
    @DrawableRes val icon: Int? = null,
    @StringRes val title: Int,
    val action: () -> Unit
)
data class ButtonSheetModel(
    @StringRes val title: Int? = null,
    val listItem : List<ItemOptionButtonSheet>
)


fun LayoutButtomSheetDialogOptionBinding.build(modelOption: ButtonSheetModel,onClick: ()-> Unit) = this.apply {


        if(modelOption.title != null){

        }
        val adapter = OptionNoteAdapter(onClick)
        recyclerViewOption.layoutManager = LinearLayoutManager(root.context, RecyclerView.VERTICAL, false)
        recyclerViewOption.adapter = adapter
        adapter.setData(modelOption.listItem)
}


