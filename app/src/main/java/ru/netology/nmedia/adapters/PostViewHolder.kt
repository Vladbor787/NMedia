package ru.netology.nmedia.adapters

import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.model.Post
import ru.netology.nmedia.model.util.formatCounts

class PostViewHolder (
    private val binding :CardPostBinding,
    private val listener: OnPostInteractionListener
): RecyclerView.ViewHolder(binding.root){
    fun bind (post: Post){
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            like.setIconResource(
                if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_like_24
            )
            like.setOnClickListener {
                listener.onLikeListener(post)
            }
            share.setOnClickListener {
                listener.onShareListener(post)
            }
            viewers.setOnClickListener {
                listener.onViewListener(post)
            }
            like.text = formatCounts(post.countLike)
            share.text = formatCounts(post.countShare)
            viewers.text = formatCounts(post.countView)
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_menu)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
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
                }.show()
            }
        }
    }
}