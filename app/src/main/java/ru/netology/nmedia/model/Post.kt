package ru.netology.nmedia.model

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Likes,
    var countShare: Int,
    var countView: Int,
    val videoLink: String?
)
data class Likes(
val likedByMe: Boolean = false,
val countLike: Int)