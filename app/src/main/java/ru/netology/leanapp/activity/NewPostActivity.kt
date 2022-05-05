package ru.netology.leanapp.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.leanapp.R
import ru.netology.leanapp.databinding.ActivityMainBinding
import ru.netology.leanapp.databinding.ActivityNewPostBinding
import ru.netology.leanapp.dto.Post

class NewPostActivity : AppCompatActivity() {
    var post: Post? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        post = intent.getParcelableExtra(POST_KEY)

        if (post?.id != 0L) {
            binding.editField.setText(post?.text)
            binding.authorField.setText(post?.author)
            binding.editField.requestFocus()
        }

        binding.editField.requestFocus()
        binding.ok.setOnClickListener {
            onOkButtonClicked(binding.editField.text, binding.authorField.text)
        }
    }

    private fun onOkButtonClicked(text: Editable, author: Editable?) {
        val intent = Intent()
        if (text.isBlank()) {
            setResult(RESULT_CANCELED, intent)
        } else {
            val newPostContent = text.toString()
            var newPostAuthor = author.toString()
            if (newPostAuthor.isNullOrEmpty()) {
                newPostAuthor = "Unknown author"
            }
            val newPost =
                if (post?.id != 0L) post?.copy(text = newPostContent, author = newPostAuthor)
                else Post(0L, newPostAuthor, newPostContent, 0)
            intent.putExtra(NEW_POST_KEY, newPost)
            setResult(RESULT_OK, intent)
        }
        finish()
    }

    object ResultContract : ActivityResultContract<Post, Post?>() {
        override fun createIntent(context: Context, input: Post) =
            Intent(context, NewPostActivity::class.java).putExtra(POST_KEY, input)

        override fun parseResult(resultCode: Int, intent: Intent?): Post? {
            return if (resultCode == Activity.RESULT_OK) {
                intent?.getParcelableExtra(NEW_POST_KEY)
            } else null
        }
    }

    private companion object {
        const val POST_KEY = "oldContent"
        const val NEW_POST_KEY = "newPost"
        const val AUTHOR_KEY = "authorKey"
    }
}