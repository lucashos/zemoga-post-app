package com.example.zemoga.feature.postlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zemoga.domain.usecase.ListPostsUseCase
import com.example.zemoga.domain.ResultState
import com.example.zemoga.domain.entities.Post
import io.reactivex.rxjava3.disposables.CompositeDisposable

class PostListViewModel(private val listPostsUseCase: ListPostsUseCase) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _postListLiveData = MutableLiveData<ResultState<List<Post>>>()

    val todoListLiveData: LiveData<ResultState<List<Post>>>
        get() = _postListLiveData

    fun listPosts() {
        listPostsUseCase.execute().subscribe({ response ->
            _postListLiveData.postValue(ResultState.Success(response))
        }, { error ->
            _postListLiveData.postValue(ResultState.Error(error.message, error))
        }).also {
            disposables.add(it)
        }

    }
}