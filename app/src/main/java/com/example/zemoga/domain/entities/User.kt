package com.example.zemoga.domain.entities

data class User(
    val name: String,
    val username: String,
    val company: Company
) {
    val authorInformation: String
        get() = "$name ($username) @ ${company.name}"
}

data class Company(
    val name: String
)