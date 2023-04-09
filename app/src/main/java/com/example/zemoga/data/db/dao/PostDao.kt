package com.example.zemoga.data.db.dao

import androidx.room.*
import com.example.zemoga.domain.entities.Post
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface PostDao {

    @Query("select * from POST where id = :postId")
    fun getById(postId: Int): Single<Post>

    @Query("select * from POST where isFavorite = true")
    fun findFavourites(): Single<List<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: Post): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<Post>): Completable

    @Delete
    fun delete(post: Post): Single<Int>
}