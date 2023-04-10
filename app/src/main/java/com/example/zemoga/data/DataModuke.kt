package com.example.zemoga.data

import androidx.room.Room
import com.example.zemoga.data.db.AppDatabase
import com.example.zemoga.data.repository.PostsRepository
import com.example.zemoga.data.service.JsonPlaceholderService
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {
    factory {
        Room.databaseBuilder(get(), AppDatabase::class.java, "zemoga-db").build()
    }

    single {
        get<AppDatabase>().postDao()
    }

    factory { get<Retrofit>().create(JsonPlaceholderService::class.java) }

    factory { PostsRepository(get(), get()) }
}