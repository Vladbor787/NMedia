package ru.netology.nmedia.model

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean = false,
    val countLike: Int,
    var countShare: Int,
    var countView: Int
)
