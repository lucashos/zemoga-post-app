package com.example.zemoga.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.zemoga.data.db.dao.PostDao
import com.example.zemoga.domain.entities.Post

@Database(entities = [Post::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun postDao(): PostDao
}