package ru.netology.nmedia.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.model.Post
import ru.netology.nmedia.model.util.formatCounts

typealias OnLikeListener = (post: Post) -> Unit
typealias OnShareListener = (post: Post) -> Unit
typealias OnViewListener = (post: Post) -> Unit


class PostViewHolder (
    private val binding :CardPostBinding,
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener,
    private val onViewListener: OnViewListener

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
                onLikeListener(post)
            }
            share.setOnClickListener {
                onShareListener(post)
            }
            viewers.setOnClickListener {
                onViewListener(post)
            }
            like.text = formatCounts(post.countLike)
            share.text = formatCounts(post.countShare)
            viewers.text = formatCounts(post.countView)
        }
    }
}