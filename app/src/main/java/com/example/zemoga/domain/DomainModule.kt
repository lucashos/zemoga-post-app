package com.example.zemoga.domain

import com.example.zemoga.domain.usecase.ToggleFavoriteUseCase
import com.example.zemoga.domain.usecase.GetUserAndCommentsUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {

    factoryOf(::GetUserAndCommentsUseCase)

    factoryOf(::ToggleFavoriteUseCase)
}