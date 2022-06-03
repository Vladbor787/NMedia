package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.model.util.AndroidUtils.hideKeyboard
import ru.netology.nmedia.model.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {
    companion object {
        var Bundle.textArg: String? by StringArg
    }

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(
            inflater,
            container,
            false
        )
        binding.edit.requestFocus()
        arguments?.textArg
            ?.let(binding.edit::setText)


        binding.ok.setOnClickListener {
            if (binding.edit.text.isBlank()) {
                Toast.makeText(activity, R.string.error_empty_content, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                viewModel.changeContent(binding.edit.text.toString())
                viewModel.save()
                requireView().hideKeyboard()
                findNavController().navigateUp()
            }
        }
        return binding.root
    }
}