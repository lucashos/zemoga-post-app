package com.example.zemoga.domain

import com.example.zemoga.domain.usecase.ToggleFavoriteUseCase
import com.example.zemoga.domain.usecase.GetUserAndCommentsUseCase
import com.example.zemoga.domain.usecase.ListPostsUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::ListPostsUseCase)

    factoryOf(::GetUserAndCommentsUseCase)

    factoryOf(::ToggleFavoriteUseCase)
}