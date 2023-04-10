package com.example.zemoga.data.repository

import com.example.zemoga.data.db.dao.PostDao
import com.example.zemoga.data.service.JsonPlaceholderService
import com.example.zemoga.domain.entities.Post
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class PostsRepository(
    private val postDao: PostDao,
    private val service: JsonPlaceholderService
) {
    val postsLiveData = postDao.listAll()

    fun refreshPosts(): Observable<List<Post>> {
        return service.listPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { postDao.list() }
            .doAfterNext(::savePosts)
    }

    private fun savePosts(posts: List<Post>) {
        postDao.insertAll(posts)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { }
            .subscribe()
    }
}