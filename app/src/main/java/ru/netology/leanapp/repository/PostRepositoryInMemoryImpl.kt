package ru.netology.leanapp.repository

import androidx.lifecycle.MutableLiveData
import ru.netology.leanapp.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {

    private var nextId = 7L

    override val data: MutableLiveData<List<Post>>

    init {
        val posts = listOf(
            Post(
                id = 1L,
                author = "123",
                text = "Test Text",
                likes = 123123
            ), Post(
                id = 2L,
                author = "First author",
                text = "Test Text222",
                likes = 123123
            ), Post(
                id = 3L,
                author = "Second author",
                text = "Test Text333",
                likes = 123123
            ), Post(
                id = 4L,
                author = "Third author",
                text = "Test Text1412412",
                likes = 123123
            ), Post(
                id = 5L,
                author = "4th author",
                text = "1412",
                likes = 123123
            ), Post(
                id = 6L,
                author = "5th author",
                text = "632325",
                likes = 123123
            )
        )
        data = MutableLiveData(posts)
    }

    private val posts
        get() = checkNotNull(data.value) {
            "Live data should be initialized with posts"
        }


    override fun likeById(id: Long) {
        data.value = posts.map {
            if (it.id == id) it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            ) else it
        }
    }

    override fun removeById(id: Long) {
        data.value = posts.filterNot { it.id == id }
    }

    override fun save(post: Post) {
        if (post.id == NEW_POST_ID) {
            insert(post)
        } else {
            update(post)
        }
    }

    private fun insert(post: Post) {
        data.value = listOf(post.copy(id = nextId++)) + posts
    }

    private fun update(post: Post) {
        data.value = posts.map {
            if (it.id == post.id) it.copy(
                text = post.text, author = post.author
            ) else it
        }
    }

    private companion object {
        private const val NEW_POST_ID = 0L
    }
}