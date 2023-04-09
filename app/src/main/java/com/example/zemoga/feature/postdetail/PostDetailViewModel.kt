package com.example.zemoga.feature.postdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zemoga.domain.entities.Post
import com.example.zemoga.domain.entities.UserAndComments
import com.example.zemoga.domain.usecase.GetUserAndCommentsUseCase
import com.example.zemoga.domain.usecase.ToggleFavoriteUseCase
import io.reactivex.rxjava3.disposables.CompositeDisposable

class PostDetailViewModel(
    private val getUserAndCommentsUseCase: GetUserAndCommentsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {
    private val disposables = CompositeDisposable()

    private val _userAndCommentsLiveData = MutableLiveData<UserAndComments>()
    val userAndCommentsLiveData: LiveData<UserAndComments>
        get() = _userAndCommentsLiveData

    private val _favoritePostLiveData = MutableLiveData<Post>()
    val favoritePostLiveData
        get() = _favoritePostLiveData


    fun getUserAndComments(post: Post) {
        getUserAndCommentsUseCase.execute(post).subscribe({ response ->
            _userAndCommentsLiveData.postValue(response)
        }, { error ->
            error.printStackTrace()
        }).also {
            disposables.add(it)
        }
    }

    fun onFavoriteClick(post: Post) {
        toggleFavoriteUseCase.execute(post)
            .subscribe { _favoritePostLiveData.postValue(post) }
            .also { disposables.add(it) }
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

}