package com.example.zemoga.feature.postlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.zemoga.RxImmediateSchedulerRule
import com.example.zemoga.data.repository.PostsRepository
import com.example.zemoga.domain.entities.Post
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class PostListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var viewModel: PostListViewModel

    private val repository: PostsRepository = mockk()

    @Before
    fun setup() {
        every { repository.postsLiveData } returns mockk()
        viewModel = PostListViewModel(repository)
    }


    @Test
    fun `when refreshing posts from api should propagate fresh data`() {
        val posts: List<Post> = mockk()
        every { repository.refreshPosts() } returns Observable.just(posts)

        viewModel.refreshPostsFromApi()

        val result = viewModel.refreshLiveData.value as List<Post>
        Assert.assertEquals(posts, result)
    }
}