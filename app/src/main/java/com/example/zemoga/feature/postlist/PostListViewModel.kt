package com.example.zemoga.feature.postlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zemoga.domain.entities.Post
import com.example.zemoga.domain.usecase.ListPostsUseCase
import io.reactivex.rxjava3.disposables.CompositeDisposable

class PostListViewModel(private val listPostsUseCase: ListPostsUseCase) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _postListLiveData = MutableLiveData<List<Post>>()

    val postListLiveData: LiveData<List<Post>>
        get() = _postListLiveData

    fun listPosts() {
        listPostsUseCase.execute().subscribe({ response ->
            _postListLiveData.postValue(response)
        }, { error ->
            error.printStackTrace()
        }).also {
            disposables.add(it)
        }

    }
}