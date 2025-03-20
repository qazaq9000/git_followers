package com.example.gitfollowers.Model.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gitfollowers.Model.Retrofit.GitHubUser
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [GitHubUser::class], version = 2, exportSchema = false)
abstract class UserDatabase: RoomDatabase(){
    abstract fun userDAO(): UserDAO

    companion object{
        @Volatile
        private var INSTANCE: UserDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context): UserDatabase {
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}