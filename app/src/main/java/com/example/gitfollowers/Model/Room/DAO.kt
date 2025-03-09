package com.example.gitfollowers.Model.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDAO {
    @Insert
    fun add(user: User)

    @Delete
    fun delete(user: User)

}