package com.example.zemoga.feature.postdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zemoga.domain.ResultState
import com.example.zemoga.domain.entities.Post
import com.example.zemoga.domain.entities.UserAndComments
import com.example.zemoga.domain.usecase.GetUserAndCommentsUseCase
import io.reactivex.rxjava3.disposables.CompositeDisposable

class PostDetailViewModel(
    private val getUserAndCommentsUseCase: GetUserAndCommentsUseCase
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _userAndCommentsLiveData = MutableLiveData<ResultState<UserAndComments>>()

    val userAndCommentsLiveData: LiveData<ResultState<UserAndComments>>
        get() = _userAndCommentsLiveData


    fun getUserAndComments(post: Post) {
        getUserAndCommentsUseCase.execute(post).subscribe({ response ->
            _userAndCommentsLiveData.postValue(ResultState.Success(response))
        }, { error ->
            _userAndCommentsLiveData.postValue(ResultState.Error(error.message, error))
        }).also {
            disposables.add(it)
        }
    }

}