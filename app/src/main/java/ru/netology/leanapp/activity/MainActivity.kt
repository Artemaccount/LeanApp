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

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostsAdapter(viewModel)

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }


        viewModel.shareEvent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, postContent)
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)

        }


        val newPostLauncher = registerForActivityResult(
            NewPostActivity.ResultContract
        ) { newPostContent ->
            newPostContent ?: return@registerForActivityResult
            viewModel.onSave(newPostContent)
        }

        viewModel.newPostScreenEvent.observe(this) {
            newPostLauncher.launch()
        }

        binding.fab.setOnClickListener { viewModel.onAdd() }
    }
}