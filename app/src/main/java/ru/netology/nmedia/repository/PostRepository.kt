package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.model.Post

interface PostRepository {
    val data: LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun viewersById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
    fun cancelEditing(post: Post)
    companion object {
        const val NEW_POST_ID = 0L
    }
}
