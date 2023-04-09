package com.example.zemoga.feature.postdetail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.zemoga.databinding.ActivityPostDetailBinding
import com.example.zemoga.databinding.ItemPostDetailCommentBinding
import com.example.zemoga.domain.ResultState
import com.example.zemoga.domain.entities.Comment
import com.example.zemoga.domain.entities.Post
import org.koin.android.ext.android.inject

class PostDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPostDetailBinding.inflate(layoutInflater) }

    private val post: Post? by lazy { intent.extras?.getParcelable(EXTRA_POST) }

    private val viewModel: PostDetailViewModel by inject()

    companion object {
        const val EXTRA_POST = "EXTRA_POST"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        post?.let { post ->
            setupObservables()
            setupPostView(post)
            setupFavoriteButton(post)
        }
    }

    private fun setupFavoriteButton(post: Post) {
        with(binding.ctvFavourite) {
            setOnClickListener {
                viewModel.onFavoriteClick(post)
            }
        }
    }

    private fun setupPostView(post: Post) {
        viewModel.getUserAndComments(post)
        binding.run {
            tvPostDetailBody.text = post.body
            tvPostDetailTitle.text = post.title
        }
    }

    private fun setupObservables() {
        viewModel.userAndCommentsLiveData.observe({ lifecycle }) { state ->
            when (state) {
                is ResultState.Success -> {
                    binding.tvPostDetailAuthor.text = state.data.user.authorInformation
                    addComments(state.data.comments)
                }
                else -> {

                }
            }
        }

        viewModel.favoritePostLiveData.observe({ lifecycle }) {
            binding.ctvFavourite.isChecked = it.isFavorite
        }
    }

    private fun addComments(comments: List<Comment>) {
        binding.ctnComments.visibility = View.VISIBLE
        comments.forEach(::addCommentView)
    }

    private fun addCommentView(comment: Comment) {
        val commentBinding = ItemPostDetailCommentBinding.inflate(layoutInflater)
        commentBinding.tvPostDetailCommentBody.text = comment.body
        commentBinding.tvPostDetailCommentAuthor.text = comment.name
        binding.ctnComments.addView(commentBinding.root)
    }
}