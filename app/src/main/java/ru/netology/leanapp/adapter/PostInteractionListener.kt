package ru.netology.leanapp.adapter

import ru.netology.leanapp.dto.Post

interface PostInteractionListener {
    fun onLike(post: Post)
    fun onRemove(post: Post)
    fun onEdit(post: Post)
    fun onShare(post: Post)
    fun onAdd()
}