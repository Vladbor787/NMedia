package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.Group
import ru.netology.nmedia.R
import ru.netology.nmedia.adapters.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.model.util.AndroidUtils.hideKeyboard
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(viewModel)
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
            viewModel.edited.observe(this) { it ->
                binding.textContent.setText(it.content)
                binding.editableText.text = it.content
                if (it.content.isNotBlank()) {
                    binding.textContent.requestFocus()
                    binding.editGroup.visibility = Group.VISIBLE
                }

                binding.buttonSave.setOnClickListener {
                    val text = binding.textContent.text?.toString().orEmpty()
                    if (text.isBlank()) {
                        Toast.makeText(
                            this,
                            getString(R.string.empty_post_error),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        return@setOnClickListener
                    }
                    viewModel.changeContent(text)
                    viewModel.save()
                    binding.textContent.clearFocus()
                    it.hideKeyboard()
                    binding.editGroup.visibility = Group.GONE
                }
                binding.cancel.setOnClickListener {
                    viewModel.cancelEditing()
                    binding.textContent.clearFocus()
                    it.hideKeyboard()
                    binding.editGroup.visibility = Group.GONE
                }
            }
        }
    }
}