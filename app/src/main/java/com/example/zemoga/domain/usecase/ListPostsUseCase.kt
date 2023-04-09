package com.example.zemoga.domain.usecase

import com.example.zemoga.data.db.dao.PostDao
import com.example.zemoga.data.service.JsonPlaceholderService
import com.example.zemoga.domain.entities.Post
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ListPostsUseCase(
    private val service: JsonPlaceholderService,
    private val postDao: PostDao
) {
    fun execute(): Single<List<Post>> {
        return service.listPosts()
            .zipWith(postDao.findFavourites()) { posts, favorites ->
                mergePosts(posts.toMutableList(), favorites.toMutableList())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun mergePosts(
        posts: MutableList<Post>,
        favorites: MutableList<Post>
    ): List<Post> {
        posts.removeIf { favorites.firstOrNull { fav -> it.id == fav.id } != null }
        favorites.sortedBy { it.id }
        favorites.addAll(posts)
        return favorites.toList()
    }
}