package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapters.OnPostInteractionListener
import ru.netology.nmedia.adapters.PostAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.util.CompanionArg.Companion.longArg
import ru.netology.nmedia.viewmodel.PostViewModel


class FeedFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)
        val adapter = PostAdapter(
            object : OnPostInteractionListener {
               override fun onEditListener(post: Post) {
                    findNavController().navigate(
                        R.id.action_feedFragment_to_newPostFragment,
                        Bundle().apply {
                            textArg = post.content
                        }
                    )
                    viewModel.edit(post)
                }

                override fun onRemoveListener(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onPostListener(post: Post) {
                    findNavController().navigate(R.id.action_feedFragment_to_postFragment,
                        Bundle().apply {
                            longArg = post.id
                        })
                }


                override fun onLikeListener(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun  onShareListener(post: Post) {
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

                override fun onViewListener(post: Post) {
                    viewModel.viewerById(post.id)
                }

                override fun onPlayVideoListener(post: Post) {
                    if (viewModel.getVideoUri(post)) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(post.videoLink)))
                    }
                }

            }
        )
        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner, adapter::submitList)
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

        return binding.root
    }

}
