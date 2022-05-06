package ru.netology.nmedia.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.model.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.model.util.formatCounts
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            binding.render(post)
        }
        binding.like.setOnClickListener {
            viewModel.onLikeClicked()
        }
        binding.share.setOnClickListener {
            viewModel.onShareClicked()
        }
        binding.viewers.setOnClickListener {
            viewModel.onViewClicked()
        }
    }
}

private fun ActivityMainBinding.render(post: Post) {
    author.text = post.author
    published.text = post.published
    content.text = post.content
    like.setIconResource(getLikeIconResID(post.likedByMe))
    like.text = formatCounts(post.countLike)
    share.text = formatCounts(post.countShare)
    viewers.text = formatCounts(post.countView)
}



@DrawableRes
private fun getLikeIconResID(liked: Boolean): Int {
    return if (liked) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_like_24
}