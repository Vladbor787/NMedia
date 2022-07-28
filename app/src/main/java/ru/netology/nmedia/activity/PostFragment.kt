package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.model.util.CompanionArg.Companion.longArg
import ru.netology.nmedia.model.util.formatCounts
import ru.netology.nmedia.viewmodel.PostViewModel


class PostFragment : Fragment(){
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(inflater, container, false)
        with(binding.postContent) {
            viewModel.data.observe(viewLifecycleOwner) { posts ->
                val post = posts.find { it.id == arguments?.longArg }
                if (post != null) {
                    author.text = post.author
                    content.text = post.content
                    published.text = post.published
                    like.text = formatCounts(post.likes)
                    viewers.text = formatCounts(post.countView)
                    share.text = formatCounts(post.countShare)
                    //like.isChecked = post.likedByMe

                    if (post.videoLink != null) {
                        videoLink.setImageResource(R.drawable.background1)
                        videoPreview.visibility = View.VISIBLE
                    }

                    menu.setOnClickListener {
                        PopupMenu(it.context, it).apply {
                            inflate(R.menu.options_menu)
                            setOnMenuItemClickListener { item ->
                                when (item.itemId) {
                                    R.id.edit -> {
                                        viewModel.edit(post)
                                        findNavController().navigate(
                                            R.id.action_postFragment_to_newPostFragment,
                                            Bundle().apply {
                                                textArg = post.content
                                            }
                                        )
                                        true
                                    }
                                    R.id.remove -> {
                                        findNavController().navigate(
                                            R.id.action_postFragment_to_feedFragment2
                                        )
                                        viewModel.removeById(post.id)
                                        true
                                    }
                                    else -> false
                                }
                            }
                        }.show()
                    }
                like.setOnClickListener {
                        viewModel.likeById(post.id)
                    }

                    share.setOnClickListener {
                        val intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, post.content)
                            type = "text/plain"
                        }
                        val shareIntent =
                            Intent.createChooser(intent, getString(R.string.chooser_share_post))
                        startActivity(shareIntent)
                        viewModel.shareById(post.id)
                    }
                    viewers.setOnClickListener {
                        viewModel.viewerById(post.id)
                    }
                    videoLink.setOnClickListener {
                        if (viewModel.getVideoUri(post)) {
                            startActivity(Intent(Intent.ACTION_VIEW, android.net.Uri.parse(post.videoLink)))
                        }
                    }
                }
            }
        }
        return binding.root
    }
}