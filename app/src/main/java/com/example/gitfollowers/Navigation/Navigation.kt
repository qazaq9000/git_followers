package com.example.gitfollowers.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.gitfollowers.Screens.FavoritesScreen
import com.example.gitfollowers.Screens.MainScreen
import com.example.gitfollowers.Screens.ProfileScreen
import com.example.gitfollowers.Screens.ResultScreen
import com.example.gitfollowers.ViewModel.AppViewModel


@Composable
fun BottomNavigationBar(navController: NavController) {

    NavigationBar(
        containerColor = Color(0xFF242424)
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            label = { Text("Search", color = Color.White, fontSize = 14.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Green,
                unselectedIconColor = Color.DarkGray
            ),
            selected = currentRoute == Routes.Main.route,
            onClick = {
                navController.navigate(Routes.Main.route) {
                    popUpTo(navController.graph.findStartDestination().id)

                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null
                )
            },
            label = { Text("Favorites", color = Color.White, fontSize = 14.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Green,
                unselectedIconColor = Color.DarkGray
            ),
            selected = currentRoute == Routes.Favorites.route,
            onClick = {
                navController.navigate(Routes.Favorites.route) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}


@Composable
fun NavigationHost(navController: NavHostController) {
    val viewModel: AppViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Routes.Main.route
    ) {
        composable(Routes.Main.route) {
            MainScreen(navController = navController)
        }
        composable(Routes.Result.route) { navBackStackEntry ->
            val username = navBackStackEntry.arguments?.getString("username")

            ResultScreen(username = username ?: return@composable, navController = navController, viewModel = viewModel)
        }
        composable(
            route = Routes.Profile.route,
            arguments = listOf(navArgument("username") { type = NavType.StringType })

        ){ navBackStackEntry ->
            val login = navBackStackEntry.arguments?.getString("username")

            ProfileScreen(
                username = login!!,
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(Routes.Favorites.route){navBackStackEntry ->
            val login = navBackStackEntry.arguments?.getString("username")
            FavoritesScreen(
                username = login!!,
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}