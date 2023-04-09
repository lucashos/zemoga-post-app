package com.example.zemoga.domain.usecase

import com.example.zemoga.data.db.dao.PostDao
import com.example.zemoga.domain.entities.Post
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class ToggleFavoriteUseCase(
    private val postDao: PostDao
) {
    fun execute(post: Post): Completable {
        post.isFavorite = !post.isFavorite
        return postDao.insert(post)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}