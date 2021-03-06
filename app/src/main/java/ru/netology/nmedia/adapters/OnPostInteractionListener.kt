package ru.netology.nmedia.adapters

import ru.netology.nmedia.dto.Post

interface OnPostInteractionListener {
    fun onEditListener(post: Post)
    fun onRemoveListener(post: Post)
    fun onPostListener(post: Post)
    fun onLikeListener(post: Post)
    fun onShareListener(post: Post)
    fun onViewListener(post: Post)
    fun onPlayVideoListener(post: Post)

}