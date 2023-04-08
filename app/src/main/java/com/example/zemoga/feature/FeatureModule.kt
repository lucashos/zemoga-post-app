package com.example.zemoga.feature

import com.example.zemoga.feature.postlist.PostListViewModel
import com.example.zemoga.feature.postdetail.PostDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val featureModule = module {
    viewModelOf(::PostListViewModel)

    viewModelOf(::PostDetailViewModel)
}