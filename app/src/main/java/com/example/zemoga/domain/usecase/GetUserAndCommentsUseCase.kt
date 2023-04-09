package com.example.zemoga.domain.usecase

import com.example.zemoga.domain.entities.Post
import com.example.zemoga.domain.entities.UserAndComments
import com.example.zemoga.data.service.JsonPlaceholderService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class GetUserAndCommentsUseCase(private val service: JsonPlaceholderService) {

    fun execute(post: Post) = service
        .getUser(post.userId)
        .zipWith(service.getComments(post.id)) { user, comments ->
            UserAndComments(user, comments)
        }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}