package com.example.zemoga.core

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val baseUrl = "https://jsonplaceholder.typicode.com"

class Network {

    private val httpClient: OkHttpClient = createHttpClient()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(configureGson()))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(httpClient)
        .build()

    private fun configureGson() = GsonBuilder()
        .create()

    private fun createHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(getLoggerInterceptor())
        .build()

    private fun getLoggerInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}