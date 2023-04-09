package com.example.zemoga.feature.postlist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zemoga.databinding.ActivityPostListBinding
import com.example.zemoga.domain.entities.Post
import com.example.zemoga.feature.postdetail.PostDetailActivity
import com.example.zemoga.feature.postdetail.PostDetailActivity.Companion.EXTRA_POST
import org.koin.android.ext.android.inject

class PostListActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPostListBinding.inflate(layoutInflater) }

    private val viewModel: PostListViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupSwipeRefresh()
        setupRecyclerView()
        setupObservables()
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.listPosts()
        }
    }

    private fun setupRecyclerView() {
        binding.rvPostList.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.listPosts()
    }

    private fun setupObservables() {
        viewModel.postListLiveData.observe({ lifecycle }) { result ->
            binding.swipeRefresh.isRefreshing = false
            binding.rvPostList.adapter = PostListAdapter(result, ::onPostClick)
        }
    }

    private fun onPostClick(post: Post) {
        goToDetailsActivity(post)
    }

    private fun goToDetailsActivity(post: Post) {
        startActivity(
            Intent(
                this,
                PostDetailActivity::class.java
            ).putExtra(EXTRA_POST, post)
        )
    }
}