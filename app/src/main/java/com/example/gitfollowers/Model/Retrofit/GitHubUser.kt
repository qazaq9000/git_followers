package com.example.gitfollowers.Model.Retrofit

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GitHubUser(
    @Json(name = "login") val login: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "avatar_url") val avatar: String?,
    @Json(name = "created_at") val since: String?,
    @Json(name = "bio") val bio: String?,
    @Json(name = "location") val location: String?,
    @Json(name = "public_repos") val repos: Int?,
    @Json(name = "gists") val gists: Int?,
    @Json(name = "followers") val followers: Int?,
    @Json(name = "following") val following: Int?

)
