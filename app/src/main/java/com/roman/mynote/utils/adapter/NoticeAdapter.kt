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

class NoticeAdapter: RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>() {
    private var listItemNotice = mutableListOf<String>()

    inner class NoticeViewHolder(val binding: LayoutItemNoticeBinding): RecyclerView.ViewHolder(binding.root){
        private var isCardExpanded = false
        fun build() {
            binding.root.setOnClickListener {
                toggleCardExpansion(this.itemView)
            }
        }

        private fun toggleCardExpansion(view: View) {
            val centerX = view.width / 2
            val centerY = view.height / 2
            val startRadius = 0f
            val endRadius = Math.hypot(centerX.toDouble(), centerY.toDouble()).toFloat()

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
                val anim = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius.toFloat(), endRadius)
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
        holder.build()

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<String>){
        this.listItemNotice = data.toMutableList()
        notifyDataSetChanged()
    }
}