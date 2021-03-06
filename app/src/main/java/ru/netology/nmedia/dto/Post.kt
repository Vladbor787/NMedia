package ru.netology.nmedia.dto

data class Post(

    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int,
    val likedByMe: Boolean = false,
    var countShare: Int,
    var countView: Int,
    val videoLink: String?
)