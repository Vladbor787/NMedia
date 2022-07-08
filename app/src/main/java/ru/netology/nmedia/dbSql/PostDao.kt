package ru.netology.nmedia.dbSql

import ru.netology.nmedia.dto.Post

interface PostDao {
    fun getAll(): List<Post>
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun viewersById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post): Post
    fun cancelEditing(post: Post)
    fun isVideo (post: Post): Boolean
}