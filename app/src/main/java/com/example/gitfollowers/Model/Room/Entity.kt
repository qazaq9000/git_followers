package com.example.gitfollowers.Model.Room

import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.PrimaryKey
import coil3.Uri

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val username: String,
    val avatar: String,
    val location: String,
    val bio: String,
    val repos: String,
    val gists: String,
    val followers: String,
    val following: String,
    val since: String
)