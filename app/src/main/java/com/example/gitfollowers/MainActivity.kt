package com.example.gitfollowers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.gitfollowers.Model.Room.UserDatabase
import com.example.gitfollowers.Screens.BottomNavigationBar
import com.example.gitfollowers.Screens.MainScreen
import com.example.gitfollowers.Screens.NavigationHost
import com.example.gitfollowers.ui.theme.GitFollowersTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "user_db"
        ).build()

        val userDAO = db.userDAO()

        setContent {
            GitFollowersTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {BottomNavigationBar(navController = navController)})
                { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)){
                        MainScreen(
                            navController = navController,
                            modifier = Modifier)
                        NavigationHost(navController)
                    }

                }
            }
        }
    }
}



