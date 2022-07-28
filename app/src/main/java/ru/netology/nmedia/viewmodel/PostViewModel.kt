package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dbSql.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryRoomImpl


private val defaultPost = Post(
    id = 0,
    author = "",
    content = "",
    published ="" ,
    likes = 0,
    likedByMe = false,
    countShare = 0,
    countView = 0,
    videoLink = ""

)

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository = PostRepositoryRoomImpl(AppDb.getInstance(context = application).postDao())
    val data = repository.getAll()
    private val edited = MutableLiveData(defaultPost)

    fun likeById(id: Long) {
        repository.likeById(id)
    }

    fun shareById(id: Long) {
        repository.shareById(id)
    }

    fun removeById(id: Long) {
        repository.removeById(id)
    }
    fun viewerById(id: Long) {
        repository.viewersById(id)
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (text == edited.value?.content) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = defaultPost
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun cancelEditing() {
        edited.value?.let {
            repository.cancelEditing(it)
        }
        edited.value = defaultPost
    }

    fun getVideoUri(post: Post): Boolean {
        return repository.isVideo(post)
    }

}