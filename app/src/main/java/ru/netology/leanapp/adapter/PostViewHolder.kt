package ru.netology.leanapp.adapter

import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.leanapp.R
import ru.netology.leanapp.databinding.PostLayoutBinding
import ru.netology.leanapp.dto.Post

class PostViewHolder(
    private val binding: PostLayoutBinding,
    private val listener: PostInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var post: Post

    private val popupMenu by lazy {
        PopupMenu(itemView.context, binding.menuButton).apply {
            inflate(R.menu.options_post)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.remove -> {
                        listener.onRemove(post)
                        true
                    }
                    R.id.edit -> {
                        listener.onEdit(post)
                        true
                    }
                    R.id.share -> {
                        listener.onShare(post)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    init {
        binding.like.setOnClickListener {
            listener.onLike(post)
        }
        binding.menuButton.setOnClickListener {
            popupMenu.show()
        }
    }

    fun bind(post: Post) {
        this.post = post
        with(binding) {
            postAuthor.text = post.author
            postText.text = post.text
            like.text = post.likes.toString()
            like.isChecked = post.likedByMe
        }
    }
}