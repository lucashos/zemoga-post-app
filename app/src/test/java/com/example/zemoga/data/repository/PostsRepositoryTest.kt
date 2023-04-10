package com.example.zemoga.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.zemoga.RxImmediateSchedulerRule
import com.example.zemoga.data.db.dao.PostDao
import com.example.zemoga.data.repository.PostsRepository
import com.example.zemoga.data.service.JsonPlaceholderService
import com.example.zemoga.domain.entities.Post
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


internal class PostsRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testSchedulerRule = RxImmediateSchedulerRule()

    private val posts = listOf<Post>(mockk(), mockk())

    private lateinit var repository: PostsRepository
    private val service: JsonPlaceholderService = mockk()
    private val dao: PostDao = mockk(relaxed = true) {
        every { listAll() } returns MutableLiveData(posts)
    }

    @Before
    fun setUp() {
        repository = PostsRepository(dao, service)
    }

    @Test
    fun `WHEN listAll THEN should return a list of posts`() {
        val result = repository.postsLiveData.value as List<Post>
        Assert.assertEquals(posts, result)
    }

    @Test
    fun `WHEN refresh posts should fetch data from API and THEN save it to database`() {
        val results = ArrayList<Post>();

        every { service.listPosts() } returns Observable.just(posts)
        every { dao.insertAll(posts) } returns Completable.complete()

        repository.refreshPosts().subscribe(results::addAll)

        verify(exactly = 1) {
            service.listPosts()
            dao.insertAll(posts)
        }
        Assert.assertEquals(posts, results)
    }

    @Test
    fun `GIVEN refreshing from api throws an error WHEN refresh posts THEN should fetch cached data from database`() {
        val results = ArrayList<Post>();

        every { service.listPosts() } returns Observable.error(Exception())
        every { dao.list() } returns Observable.just(posts)

        repository.refreshPosts().subscribe(results::addAll)

        verify(exactly = 1) {
            service.listPosts()
            dao.insertAll(posts)
            dao.list()
        }
        Assert.assertEquals(posts, results)
    }
}