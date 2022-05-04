package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.DrawableRes

import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netology.ru",
            published = "21 мая в 18:36",
            likeByMe = false,
            countLike = 999,
            countShare = 988,
            countViews = 999
        )
        binding.render(post)
        with(binding) {
            like.setOnClickListener{
                post.likeByMe = !post.likeByMe
                like.setIconResource(getLikeIconResID(post.likeByMe))
                changeLikeCount(post.likeByMe, post)
                like.text = formatCounts(post.countLike)
            }
            share.setOnClickListener {
                post.countShare += 1
                share.text = formatCounts(post.countShare)
            }
            viewers.setOnClickListener {
                post.countViews += 1
                viewers.text = formatCounts(post.countViews)
            }
        }
    }

    private fun ActivityMainBinding.render(post: Post) {
        author.text = post.author
        published.text = post.published
        content.text = post.content
        like.setIconResource(getLikeIconResID(post.likeByMe))
        like.text = formatCounts(post.countLike)
        share.text = formatCounts(post.countShare)
        viewers.text = formatCounts(post.countViews)
    }

    @DrawableRes
    private fun getLikeIconResID(liked: Boolean): Int {
        return if (liked) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_like_24
    }

    private fun changeLikeCount(liked: Boolean, post: Post) {
        if (liked) post.countLike += 1 else post.countLike -= 1
    }

    private fun formatCounts(counter: Int): String {
        return when (counter) {
            in 0..999 -> "$counter"
            in 1_000..1_099 -> "1K"
            in 1_100..9_999 -> "${counter / 
                    1000}.${counter / 100 % 10}K"
            in 10_000..999_999 -> "${counter / 1000}K"
            in 1_000_000..1_099_999 -> "1M"
            else -> "${counter / 1_000_000}.${counter / 100_000 % 10}M"
        }
    }
}