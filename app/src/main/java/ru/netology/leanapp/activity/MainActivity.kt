package ru.netology.leanapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.leanapp.R
import ru.netology.leanapp.adapter.PostsAdapter
import ru.netology.leanapp.databinding.ActivityMainBinding
import ru.netology.leanapp.dto.Post
import ru.netology.leanapp.model.PostViewModel
import ru.netology.leanapp.utils.hideKeyBoard

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //region binding/adapter
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(viewModel)
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        //endregion
        //region shareEvent
        viewModel.shareEvent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, postContent)
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }
        //endregion

        val postLauncher = registerForActivityResult(
            NewPostActivity.ResultContract
        ) { newPost ->
            newPost ?: return@registerForActivityResult
            viewModel.onSave(newPost)
        }

        viewModel.editPostScreenEvent.observe(this) { post ->
            postLauncher.launch(post)
        }

        viewModel.newPostScreenEvent.observe(this) {
            postLauncher.launch(viewModel.emptyPost())
        }

        binding.fab.setOnClickListener { viewModel.onAdd() }
    }
}