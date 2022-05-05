package ru.netology.leanapp.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.leanapp.adapter.PostInteractionListener
import ru.netology.leanapp.dto.Post
import ru.netology.leanapp.repository.PostRepository
import ru.netology.leanapp.repository.PostRepositoryInMemoryImpl
import ru.netology.leanapp.utils.SingleLiveEvent

class PostViewModel : ViewModel(), PostInteractionListener {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data by repository::data

    val editPost = MutableLiveData<Post>()

    val shareEvent = SingleLiveEvent<String>()

    val newPostScreenEvent = SingleLiveEvent<Unit>()
    val editPostScreenEvent = SingleLiveEvent<Post>()

    override fun onLike(post: Post) = repository.likeById(post.id)
    override fun onRemove(post: Post) = repository.removeById(post.id)

    override fun onEdit(post: Post) {
        editPostScreenEvent.value = post
    }

    override fun onShare(post: Post) {
        shareEvent.value = post.author + ": " + post.text
    }

    override fun onAdd() {
        newPostScreenEvent.call()
    }

    fun onSave(post: Post) {
        repository.save(post)
        this.editPost.value = null
    }

    fun emptyPost() = Post(
        id = 0L,
        author = "",
        likes = 0,
        text = ""
    )
}