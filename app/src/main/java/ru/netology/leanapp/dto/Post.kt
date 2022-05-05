package ru.netology.leanapp.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
    val id: Long,
    val author: String?,
    val text: String?,
    val likes: Int,
    val likedByMe: Boolean = false
) : Parcelable
