package ru.netology.nmedia.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.adapters.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter (
            onLikeListener =  { viewModel.likeById(it.id) },
            onShareListener = { viewModel.shareById(it.id) },
            onViewListener =  { viewModel.viewersById(it.id) }
        )
        binding.list.adapter = adapter
        viewModel.data.observe(this){ posts ->
            adapter.submitList(posts)
        }
    }

}