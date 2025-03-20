package com.example.gitfollowers

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.gitfollowers.Model.Room.UserDAO
import com.example.gitfollowers.Model.Room.UserDatabase
import com.example.gitfollowers.Navigation.BottomNavigationBar
import com.example.gitfollowers.Navigation.NavigationHost
import com.example.gitfollowers.ViewModel.AppViewModel
import com.example.gitfollowers.ui.theme.GitFollowersTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val db = UserDatabase.getInstance(applicationContext)
            val userDao = db.userDAO()
            val viewModel = ViewModelProvider(this, ViewModelFactory(userDao))
                .get(AppViewModel::class.java)

            setContent {
                GitFollowersTheme {
                    CompositionLocalProvider(LocalViewModel provides viewModel) {
                        val navController = rememberNavController()
                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                            bottomBar = { BottomNavigationBar(navController = navController) }
                        ) { innerPadding ->
                            Box(modifier = Modifier.padding(innerPadding)) {
                                NavigationHost(navController)
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Initialization failed", e)
            // Show error UI or toast
        }
    }
}

class ViewModelFactory(private val userDao: UserDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

// CompositionLocal for ViewModel access
val LocalViewModel = staticCompositionLocalOf<AppViewModel> {
    error("No ViewModel provided")
}



