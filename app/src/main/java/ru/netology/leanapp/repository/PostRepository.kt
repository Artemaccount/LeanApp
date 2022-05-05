package ru.netology.leanapp.repository

import androidx.lifecycle.LiveData
import ru.netology.leanapp.dto.Post

interface PostRepository {
    val data: LiveData<List<Post>>
    fun likeById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
}