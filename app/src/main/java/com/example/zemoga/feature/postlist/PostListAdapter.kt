package com.example.zemoga.feature.postlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.example.zemoga.R
import com.example.zemoga.databinding.ItemFavoriteHeaderBinding
import com.example.zemoga.databinding.ItemPostBinding
import com.example.zemoga.databinding.ItemPostsHeaderBinding
import com.example.zemoga.domain.entities.Post

private const val FAVORITES_HEADER = 1
private const val POSTS_HEADER = 2
private const val POST_ITEM = 3

class PostListAdapter(
    items: List<Post>,
    private val onItemClick: (Post) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {
    private val posts = items.filter { !it.isFavorite }.toMutableList()
    private val favorites = items.filter { it.isFavorite }.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            FAVORITES_HEADER -> {
                val binding = ItemFavoriteHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FavoriteHeaderViewHolder(binding)
            }
            POSTS_HEADER -> {
                val binding = ItemPostsHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                PostHeaderViewHolder(binding) {
                    posts.clear()
                    notifyDataSetChanged()
                }
            }
            else -> {
                val binding: ItemPostBinding = ItemPostBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                PostListViewHolder(binding, onItemClick, ::onDeleteClick)
            }
        }
    }

    override fun getItemCount(): Int {
        var count = posts.size + 1
        if (favorites.isNotEmpty()) {
            count += favorites.size + 1
        }
        return count
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is PostHeaderViewHolder -> holder.bind(posts.isNotEmpty())
            is FavoriteHeaderViewHolder -> holder.bind()
            is PostListViewHolder -> {
                val post = if (favorites.isNotEmpty()) {
                    favorites.elementAtOrNull(position - 1) ?: posts[position - (favorites.size + 2)]
                } else {
                    posts[position - 1]
                }
                holder.bind(post)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (favorites.isNotEmpty()) {
            getViewTypeWithFavorite(position)
        } else {
            getGeneralPostViewType(position)
        }
    }

    private fun getViewTypeWithFavorite(position: Int): Int {
        if (position == 0) return FAVORITES_HEADER
        if (position <= favorites.size) return POST_ITEM
        return getGeneralPostViewType(position - (favorites.size + 1))
    }

    private fun getGeneralPostViewType(position: Int): Int {
        return if (position == 0) POSTS_HEADER else POST_ITEM
    }

    private fun onDeleteClick(post: Post) {
        val index = posts.indexOf(post)
        posts.remove(post)
        notifyItemRemoved(index)
    }
}

class PostHeaderViewHolder(
    private val binding: ItemPostsHeaderBinding,
    private val onActionClick: (() -> Unit)? = null
) : ViewHolder(binding.root) {
    fun bind(isActionVisible: Boolean) {
        binding.postHeader.text = binding.getString(R.string.posts_header)
        binding.postAction.text = binding.getString(R.string.posts_clear_list)
        binding.postAction.setVisible(isActionVisible)
        binding.postAction.setOnClickListener {
            onActionClick?.invoke()
        }
    }
}

class FavoriteHeaderViewHolder(
    private val binding: ItemFavoriteHeaderBinding
) : ViewHolder(binding.root) {
    fun bind() {
        binding.postHeader.text = binding.getString(R.string.favorites_header)
    }
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


private fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

private fun ViewBinding.getString(resId: Int): String = this.root.context.getString(resId)
