package com.example.zemoga.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.zemoga.data.db.dao.PostDao
import com.example.zemoga.domain.entities.Post
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
internal class AppDatabaseTest {
    private lateinit var db: AppDatabase
    private lateinit var dao: PostDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        dao = db.postDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun whenInsertAllShouldAddAllPostsToDatabase() {
        val post1 = Post(1, 1, "post1", "post1", false)
        val post2 = Post(2, 2, "post2", "post2", false)
        val posts = listOf(post1, post2)

        dao.insertAll(posts).test()

        val result = dao.list().blockingFirst()

        Assert.assertEquals(posts, result)
    }
}