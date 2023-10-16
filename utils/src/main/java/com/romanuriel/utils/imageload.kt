package com.romanuriel.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.bumptech.glide.request.transition.Transition

fun ImageView.loadCircular(url: String?) = Glide.with(this)
    .load(url)
    .transition(
        DrawableTransitionOptions.withCrossFade(
            DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        )
    )
    .placeholder(R.drawable.ic_person_24)
    .error(com.google.android.material.R.drawable.ic_search_black_24)
    .transform(CircleCrop())
    .into(this)

fun ImageView.loadImage(url: Any?)  = Glide.with(this)
    .load(url)
    .transition(
        DrawableTransitionOptions.withCrossFade(
            DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        )
    )
    .placeholder(R.drawable.ic_person_24)
    .error(R.drawable.ic_arrow_back)

    .into(this)

fun ImageView.loadImage(url: Any?, callback: (Bitmap?) -> Unit) = Glide.with(this)
    .load(url)
    .transition(
        DrawableTransitionOptions.withCrossFade(
            DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        )
    )
    .placeholder(com.google.android.material.R.drawable.ic_keyboard_black_24dp)
    .error(com.google.android.material.R.drawable.mtrl_ic_error)
    .into(object : CustomTarget<Drawable?>(){
        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>?) {
            callback(resource.toBitmap())
        }

        override fun onLoadCleared(placeholder: Drawable?) {}

    })

fun ImageView.colorIconDraw(@ColorRes color: Int) = this.resources.getColor(color)
