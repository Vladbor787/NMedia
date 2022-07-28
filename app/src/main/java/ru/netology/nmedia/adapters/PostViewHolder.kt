package ru.netology.nmedia.adapters

import android.view.View.VISIBLE
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.util.formatCounts

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: OnPostInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var post: Post
    private val popupMenu by lazy {
        android.widget.PopupMenu(itemView.context, binding.menu).apply {
            inflate(R.menu.options_menu)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.remove -> {
                        listener.onRemoveListener(post)
                        true
                    }
                    R.id.edit -> {
                        listener.onEditListener(post)
                        true
                    }
                    else -> false
                }

            }
        }
    }

    init {
        binding.like.setOnClickListener { listener.onLikeListener(post) }
        binding.share.setOnClickListener { listener.onShareListener(post) }
        binding.viewers.setOnClickListener { listener.onViewListener(post) }
        binding.buttonPlay.setOnClickListener { listener.onPlayVideoListener(post) }
        binding.videoLink.setOnClickListener { listener.onPlayVideoListener(post) }
        binding.videoLink.setOnClickListener { listener.onPostListener(post) }

    }

    fun bind(post: Post) {
        this.post = post

        with(binding) {
            author.text = post.author
            content.text = post.content
            published.text = post.published
            like.text = formatCounts(post.likes)
            viewers.text = formatCounts(post.countView)
            share.text = formatCounts(post.countShare)
            //like.isChecked = post.likedByMe
            menu.setOnClickListener { popupMenu.show() }
            if (post.videoLink != null) {
                videoLink.setImageResource(R.drawable.background1)
                videoPreview.visibility = VISIBLE
            }

        }

    }
}
