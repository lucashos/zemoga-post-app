package com.example.zemoga.domain.usecase

import com.example.zemoga.domain.service.JsonPlaceholderService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ListPostsUseCase(private val service: JsonPlaceholderService) {
    fun execute() = service.listPosts()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}