package ru.netology.leanapp.dto

data class Post(
    val id: Long,
    val author: String,
    val text: String,
    val likes: Int,
    val likedByMe: Boolean = false
)
