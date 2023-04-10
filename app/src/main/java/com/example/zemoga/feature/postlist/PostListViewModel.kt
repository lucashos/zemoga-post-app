package com.example.zemoga.feature.postlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zemoga.data.repository.PostsRepository
import com.example.zemoga.domain.entities.Post
import io.reactivex.rxjava3.disposables.CompositeDisposable

class PostListViewModel(
    private val repository: PostsRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _refreshLiveData = MutableLiveData<List<Post>>()
    val refreshLiveData
        get() = _refreshLiveData

    val postListLiveData = repository.postsLiveData

    fun refreshPostsFromApi() {
        repository.refreshPosts().subscribe({
            _refreshLiveData.postValue(it)
        }, {
            it.printStackTrace()
        }).also { disposables.add(it) }
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}