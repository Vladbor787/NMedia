package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapters.OnPostInteractionListener
import ru.netology.nmedia.model.Likes
import ru.netology.nmedia.model.Post
import ru.netology.nmedia.model.util.SingleLiveEvent
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl



class PostViewModel : ViewModel (),OnPostInteractionListener{
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data by repository::data
   // private val edited = MutableLiveData(defaultPost)
    private val currentPost = MutableLiveData<Post?>(null)
    val sharePostContent = SingleLiveEvent<String>()
    val navigateToPostContentScreenEvent = SingleLiveEvent<Unit?>()
    val navigateToEditPostContentScreenEvent = SingleLiveEvent<String?>()
    val playVideoLink = SingleLiveEvent<String?>()

   // fun changeContent(content: String) {
    //    val text = content.trim()
    //    if (text == edited.value?.content) {
    //        return
    //    }
    //    edited.value = edited.value?.copy(content = text)
   // }

    fun onSaveButtonClicked(content: String) {
        if(content.isBlank()) return
        val newPost = currentPost.value?.copy(content = content)?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Author",
            content = content,
            published ="Date" ,
            likes = Likes(countLike = 0, likedByMe = false),
            countShare = 0,
            countView = 0,
            videoLink = "https://youtu.be/Ed0Xdi_xdfw"

        )
        repository.save(newPost)
        currentPost.value = null
    }
   // fun cancelEditing() {
   //     edited.value?.let {
   //         repository.cancelEditing(it)
   //     }
   //     edited.value = defaultPost
   // }

    override fun onEditListener(post: Post) {
        currentPost.value = post
        navigateToEditPostContentScreenEvent.value = post.content
    }
    fun onAddClicked() {
        currentPost.value = null
        navigateToPostContentScreenEvent.call()
    }

    override fun onRemoveListener(post: Post) {
        repository.removeById(post.id)
    }

    override fun onLikeListener(post: Post) {
        repository.likeById(post.id)
    }

    override fun onShareListener(post: Post) {
        sharePostContent.value = post.content
        repository.shareById(post.id)
    }

    override fun onViewListener(post: Post) {
        repository.viewersById(post.id)
    }

    override fun onPlayVideoListener(post: Post) {
        if (post.videoLink!=null) {
            playVideoLink.value = post.videoLink
        }
    }

}