package com.roman.mynote.utils

import androidx.annotation.DrawableRes
import com.roman.mynote.databinding.LayoutInfoProfileBinding

data class ProfileData(
    val id: Long,
    @DrawableRes val avatar: Int? = com.romanuriel.utils.R.drawable.ic_person_24,
    val userName: String,
    val moreAction: () -> Unit
)

fun LayoutInfoProfileBinding.set(data: ProfileData) = this.apply {
    textUsername.text = data.userName
    imageAvatar.setBackgroundResource(data.avatar!!)
}