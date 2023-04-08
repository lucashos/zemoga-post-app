package com.example.zemoga.domain

import com.example.zemoga.domain.service.JsonPlaceholderService
import com.example.zemoga.domain.usecase.ListPostsUseCase
import com.example.zemoga.domain.usecase.GetUserAndCommentsUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import retrofit2.Retrofit

val domainModule = module {
    factory { get<Retrofit>().create(JsonPlaceholderService::class.java) }

    factoryOf(::ListPostsUseCase)

    factoryOf(::GetUserAndCommentsUseCase)
}