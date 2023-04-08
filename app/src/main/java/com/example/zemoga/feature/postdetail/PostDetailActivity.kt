package com.example.zemoga.feature.postdetail

import android.os.Bundle
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

        viewModel.userAndCommentsLiveData.observe({ lifecycle }) {state ->
            when(state) {
                is ResultState.Success -> {
                    binding.tvPostDetailAuthor.text = state.data.user.authorInformation
                    addComments(state.data.comments)
                }
                else -> {

                }
            }
        }

        post?.let { post ->
            viewModel.getUserAndComments(post)
            binding.run {
                tvPostDetailBody.text = post.body
                tvPostDetailTitle.text = post.title
            }
        }
    }

    private fun addComments(comments: List<Comment>) {

        comments.forEach(::addCommentView)
    }

    private fun addCommentView(comment: Comment) {
        val commentBinding = ItemPostDetailCommentBinding.inflate(layoutInflater)
        commentBinding.tvPostDetailCommentBody.text = comment.body
        commentBinding.tvPostDetailCommentAuthor.text = comment.name
        binding.postDetailContent.addView(commentBinding.root)
    }
}