package com.example.zemoga.domain.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "POST")
data class Post(
    val userId: Int,
    @PrimaryKey val id: Int,
    val title: String,
    val body: String,
    var isFavorite: Boolean = false
): Parcelable
