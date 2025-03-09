package com.example.gitfollowers.Model.Retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {

    @GET("users/{user}/followers")
    suspend fun getUserFollowers(
        @Path("user") login: String?
    ): List<GithubFollower>

    @GET("/users/{user}")
    suspend fun getUser(@Path("user") login: String?): GitHubUser?
}