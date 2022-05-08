package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapters.OnPostInteractionListener
import ru.netology.nmedia.model.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl
private val defaultPost = Post(
    id = 0L,
    author = "",
    content = "",
    published = "",
    likedByMe = false,
    countLike = 0,
    countShare = 0,
    countView = 0
)
class PostViewModel : ViewModel (),OnPostInteractionListener{
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    val edited = MutableLiveData(defaultPost)

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
    fun cancelEditing() {
        edited.value?.let {
            repository.cancelEditing(it)
        }
        edited.value = defaultPost
    }

    override fun onEditListener(post: Post) {
       edited.value = post
    }

    override fun onRemoveListener(post: Post) {
        repository.removeById(post.id)
    }

    override fun onLikeListener(post: Post) {
        repository.likeById(post.id)
    }

    override fun onShareListener(post: Post) {
        repository.shareById(post.id)
    }

    override fun onViewListener(post: Post) {
        repository.viewersById(post.id)
    }

}