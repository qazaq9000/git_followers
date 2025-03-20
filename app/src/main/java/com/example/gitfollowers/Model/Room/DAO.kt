package com.example.gitfollowers.Model.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gitfollowers.Model.Retrofit.GitHubUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(user: GitHubUser)

    @Delete
    suspend fun delete(user: GitHubUser)

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): List<GitHubUser>

    @Query("SELECT * FROM user_table WHERE login = :login")
    suspend fun getUserByLogin(login: String): GitHubUser
}