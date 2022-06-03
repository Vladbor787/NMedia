package ru.netology.nmedia.repository


import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.model.Likes
import ru.netology.nmedia.model.Post


class PostRepositoryInMemoryImpl: PostRepository {
    private var nextId = GENERATED_POSTS_AMOUNT.toLong()
    private val posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    override val data = MutableLiveData(List(GENERATED_POSTS_AMOUNT) { index ->
        Post(
            id = index + 1L,
            author = "Автор",
           content = "Текст поста $index",
            published = "Дата поста $index",
            likes = Likes(countLike = 0, likedByMe = false),
            countShare = 0,
            countView = 0,
            videoLink = "https://youtu.be/Ed0Xdi_xdfw"
        )
    })

    override fun likeById(id: Long) {

        data.value = posts.map {
            if (it.id != id) it else {
                val currentLikes = checkNotNull(it.likes) {
                    "Data value should not be null"
                }
                val userLikes = !currentLikes.likedByMe
                val count: Int = if (userLikes) {
                    currentLikes.countLike + 1
                } else {
                    currentLikes.countLike - 1
                }
                val likes = currentLikes.copy(likedByMe = userLikes, countLike = count)
                it.copy(likes = likes)
            }
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
    private companion object {
        const val GENERATED_POSTS_AMOUNT = 10
    }
}