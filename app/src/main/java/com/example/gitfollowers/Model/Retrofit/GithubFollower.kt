package com.example.gitfollowers.Model.Retrofit


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GithubFollower(
    @Json(name = "avatar_url")
    val avatarUrl: String? = "",
    @Json(name = "followers_url")
    val followersUrl: String? = "",
    @Json(name = "following_url")
    val followingUrl: String? = "",
    @Json(name = "login")
    val login: String? = "",
)