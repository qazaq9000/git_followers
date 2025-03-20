package com.example.gitfollowers.Model.Retrofit

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@Entity(tableName = "user_table")
@JsonClass(generateAdapter = true)
data class GitHubUser(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "login")
    @Json(name = "login") var login: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "avatar_url") val avatar: String?,
    @Json(name = "created_at") val since: String?,
    @Json(name = "bio") val bio: String?,
    @Json(name = "location") val location: String?,
    @Json(name = "public_repos") val repos: Int?,
    @Json(name = "gists") val gists: Int?,
    @Json(name = "followers") val followers: Int?,
    @Json(name = "following") val following: Int?

) {
    init {
        login?.let { require(it.isNotEmpty()) {"Login cannot be empty"} }
    }
}
