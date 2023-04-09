package com.example.zemoga.feature.postlist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.zemoga.databinding.ItemPostBinding
import com.example.zemoga.domain.entities.Post

class PostListAdapter(
    private val items: List<Post>,
    private val onItemClick: (Post) -> Unit,
    private val onPostDeleted: (Post) -> Unit
) : RecyclerView.Adapter<PostListAdapter.PostListViewHolder>() {
    private val posts = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        val binding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostListViewHolder(binding, onItemClick, ::onDeleteClick)
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    private fun onDeleteClick(post: Post) {
        val index = posts.indexOf(post)
        posts.remove(post)
        notifyItemRemoved(index)
        onPostDeleted(post)
    }

    class PostListViewHolder(
        private val binding: ItemPostBinding,
        private val onItemClick: (Post) -> Unit,
        private val onDeleteClick: (Post) -> Unit
    ) : ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.run {
                postTitle.text = post.title
                postBody.text = post.body
                postFavorite.setVisible(post.isFavorite)
                postDelete.setVisible(!post.isFavorite)
                root.setOnClickListener { onItemClick(post) }
                postDelete.setOnClickListener {
                    onDeleteClick(post)
                }
            }
        }
    }
}

private fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}
