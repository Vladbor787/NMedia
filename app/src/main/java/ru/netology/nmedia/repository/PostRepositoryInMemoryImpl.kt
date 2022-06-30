package ru.netology.nmedia.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post


class PostRepositoryInMemoryImpl: PostRepository {
    private var nextId = GENERATED_POSTS_AMOUNT.toLong()
    private val posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    private val data = MutableLiveData(List(GENERATED_POSTS_AMOUNT) { index ->
        Post(
            id = index + 1L,
            author = "Автор",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. $index",
            published = "1$index августа 202$index",
            likes = index+100,
            likedByMe = false,
            countShare = 0,
            countView = 0,
            videoLink = "https://youtu.be/Ed0Xdi_xdfw"
        )
    })

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        data.value = data.value?.map {
            if (it.id == id) it.copy(
                likedByMe = !it.likedByMe,
                likes =  if (it.likedByMe) it.likes.dec() else it.likes.inc()
            )
            else it
        }
    }

    override fun shareById(id:Long) {
        data.value = posts.map { if (it.id != id) it else it.copy(countShare = it.countShare + 1) }
    }

    override fun viewersById(id:Long) {
        data.value = posts.map { if (it.id != id) it else it.copy(countView = it.countView + 1) }
    }

    override fun removeById(id: Long) {data.value = posts.filter { it.id != id }}

    override fun save(post: Post) {
        if (post.id == 0L) {
            data.value = listOf(post.copy(id = nextId++, author = "Me", published = "Now")) +
                    data.value.orEmpty()
            return
        }
        data.value = data.value?.map {
            if (it.id == post.id) it.copy(
                content = post.content
            )
            else it
        }
    }

    override fun cancelEditing(post: Post) {
        data.value = data.value?.map {
            it.copy(content = it.content)
        }
    }

    override fun isVideo(post: Post): Boolean {
        return (!post.videoLink.isNullOrEmpty())
    }
    private companion object {
        const val GENERATED_POSTS_AMOUNT = 10
    }
}