package com.example.zemoga.data.service

import com.example.zemoga.domain.entities.Comment
import com.example.zemoga.domain.entities.Post
import com.example.zemoga.domain.entities.User
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonPlaceholderService {
    @GET("/posts")
    fun listPosts(): Single<List<Post>>

    @GET("/posts/{postId}/comments")
    fun getComments(@Path("postId") postId: Int): Single<List<Comment>>

    @GET("/users/{userId}")
    fun getUser(@Path("userId") userId: Int): Single<User>
}