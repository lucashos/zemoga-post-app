package com.example.zemoga.feature.postlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.zemoga.databinding.ItemPostBinding
import com.example.zemoga.domain.entities.Post

class PostListAdapter(
    private val posts: List<Post>,
    private val onItemClick: (Post) -> Unit
) : RecyclerView.Adapter<PostListAdapter.PostListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        val binding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostListViewHolder(binding, onItemClick)
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    class PostListViewHolder(
        private val binding: ItemPostBinding,
        private val onItemClick: (Post) -> Unit
    ) : ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.run {
                postTitle.text = post.title
                postBody.text = post.body
                root.setOnClickListener { onItemClick(post) }
            }
        }
    }
}