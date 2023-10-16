package com.roman.mynote.utils.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roman.mynote.R
import com.roman.mynote.databinding.LayoutItemNoticeBinding
import com.roman.mynote.utils.model.ItemNewModel
import com.romanuriel.utils.colorIconDraw
import com.romanuriel.utils.loadImage
import kotlin.math.hypot

class NoticeAdapter: RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>() {
    private var listItemNotice = mutableListOf<ItemNewModel>()

    inner class NoticeViewHolder(val binding: LayoutItemNoticeBinding): RecyclerView.ViewHolder(binding.root){
        private var isCardExpanded = false
        fun build(item: ItemNewModel) {
            binding.imageReference.setImageDrawable(binding.root.resources.getDrawable(item.type.icon))
            binding.textTitle.text = item.title
            binding.textDescription.text = item.description
        }

        private fun toggleCardExpansion(view: View) {
            val centerX = view.width / 2
            val centerY = view.height / 2
            val startRadius = 0f
            val endRadius = hypot(centerX.toDouble(), centerY.toDouble()).toFloat()

            if (isCardExpanded) {
                val anim = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, endRadius, startRadius)
                anim.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        view.visibility = View.GONE
                        isCardExpanded = false
                    }
                })
                anim.start()
            } else {
                val anim = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius)
                view.visibility = View.VISIBLE
                anim.start()
                isCardExpanded = true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_notice, parent, false)
        return NoticeViewHolder(LayoutItemNoticeBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return listItemNotice.size
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        val itemNotice = listItemNotice[position]
        holder.build(itemNotice)

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<ItemNewModel>){
        this.listItemNotice = data.toMutableList()
        notifyDataSetChanged()
    }
}