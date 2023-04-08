package com.example.zemoga.domain.entities

data class UserAndComments(
    val user: User,
    val comments: List<Comment>
)