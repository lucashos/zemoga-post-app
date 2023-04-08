package com.example.zemoga.feature.postlist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zemoga.databinding.ActivityPostListBinding
import com.example.zemoga.domain.ResultState
import com.example.zemoga.feature.postdetail.PostDetailActivity
import com.example.zemoga.feature.postdetail.PostDetailActivity.Companion.EXTRA_POST
import org.koin.android.ext.android.inject

class PostListActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPostListBinding.inflate(layoutInflater) }

    private val viewModel: PostListViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.listPosts()

        binding.rvPostList.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        viewModel.todoListLiveData.observe({ lifecycle }) { result ->
            when(result) {
                is ResultState.Success -> {
                    binding.rvPostList.adapter = PostListAdapter(result.data) { post ->
                        startActivity(
                            Intent(
                                this,
                                PostDetailActivity::class.java
                            ).putExtra(EXTRA_POST, post)
                        )
                    }
                }
                is ResultState.Error -> {

                }
                is ResultState.Loading -> {

                }
            }
        }

    }
}