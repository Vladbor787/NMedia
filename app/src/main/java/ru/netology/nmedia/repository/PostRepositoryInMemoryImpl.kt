package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.model.Post

class PostRepositoryInMemoryImpl: PostRepository {
    private var nextId = 1L
    private var posts = listOf(
        Post(
        id = 5,
        author = "Нетология. Университет интернет-профессий будущего",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        published = "21 мая в 18:36",
        likedByMe = false,
        countLike = 100,
        countShare = 50,
        countView = 0
    ),
        Post(
            id = 4,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Делиться впечатлениями о любимых фильмах легко, а что если рассказать так, чтобы все заскучали \uD83D\uDE34\n",
            published = "22 сентября в 10:14",
            likedByMe = false,
            countLike = 0,
            countShare = 5000,
            countView = 0
        ),
        Post(
            id = 3,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Языков программирования много, и выбрать какой-то один бывает нелегко. Собрали подборку статей, которая поможет вам начать, если вы остановили свой выбор на JavaScript.",
            published = "19 сентября в 10:24",
            likedByMe = false,
            countLike = 0,
            countShare = 0,
            countView = 0
        ),
        Post(
            id = 2,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Знаний хватит на всех: на следующей неделе разбираемся с разработкой мобильных приложений, учимся рассказывать истории и составлять PR-стратегию прямо на бесплатных занятиях \uD83D\uDC47",
            published = "18 сентября в 10:12",
            likedByMe = false,
            countLike = 500,
            countShare = 0,
            countView = 0
        ),
        Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,
            countLike = 0,
            countShare = 0,
            countView = 0
        ),
    )
private val data = MutableLiveData(posts)
    override fun getAll(): LiveData<List<Post>> = data


    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(likedByMe = !it.likedByMe,
                countLike = if (it.likedByMe) it.countLike-1 else it.countLike+1)
        }
        data.value = posts
    }

    override fun shareById(id:Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(countShare = it.countShare + 1)
        }
        data.value = posts
    }

    override fun viewersById(id:Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(countView = it.countView + 1)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts=posts.filter { it.id != id }
        data.value = posts
    }

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
}