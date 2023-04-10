package com.example.zemoga.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.zemoga.domain.entities.Post
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface PostDao {

    @Query("select * from POST where id = :postId")
    fun getById(postId: Int): Single<Post>

    @Query("select * from POST")
    fun listAll(): LiveData<List<Post>>

    @Query("select * from POST")
    fun list(): Observable<List<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: Post): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(posts: List<Post>): Completable
}