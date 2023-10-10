package com.roman.mynote.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roman.mynote.R
import com.roman.mynote.databinding.LayoutItemSettingBinding

class AdapterSetting: RecyclerView.Adapter<AdapterSetting.SettingViewHolder>() {
    private var list = mutableListOf<String>("General","Notificaciones","Datos","Accesibilidad","Ayuda","Mas Informacion")
    inner class SettingViewHolder(val binding: LayoutItemSettingBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SettingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_setting,parent,false)
        return SettingViewHolder(LayoutItemSettingBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {

    }

    fun setData(toList: List<String>){

    }
}