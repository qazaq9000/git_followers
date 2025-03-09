package com.example.gitfollowers.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.gitfollowers.Navigation.Routes
import com.example.gitfollowers.R
import com.example.gitfollowers.ViewModel.AppViewModel
import com.example.gitfollowers.ui.theme.GitFollowersTheme

@Composable
fun MainScreen(navController: NavHostController, modifier: Modifier = Modifier) {

    var username by remember { mutableStateOf("Maloy-Android") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF000000), shape = RectangleShape)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            Image(
                painter = painterResource(R.drawable.github1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(0.8f),

                )
            Text(
                "Followers",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                shape = RoundedCornerShape(16.dp),
                label = {
                    Text(
                        "Enter a username",
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White

                ),
                modifier = Modifier.width(320.dp)
            )

            Button(
                onClick = {navController.navigate(Routes.Result.createRoute(username)){
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                } },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF599649),
                    contentColor = Color.White
                ), modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .width(320.dp)
                    .height(100.dp)
                    .padding(bottom = 50.dp)
            ) {
                Text(
                    "Get Followers",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

        }

    }
}


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
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
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
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}


@Composable
fun NavigationHost(navController: NavHostController, viewModel: AppViewModel = viewModel()) {

    val ctx = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Routes.Main.route
    ) {
        composable(Routes.Main.route) {
            MainScreen(navController = navController)
        }
        composable(Routes.Result.route) { navBackStackEntry ->
            val username = navBackStackEntry.arguments?.getString("username")
                if (username == null) {
                    LaunchedEffect(Unit) {
                        Toast.makeText(ctx, "username is required", Toast.LENGTH_LONG).show()
                        navController.popBackStack()
                    }
                }else {
                    ResultScreen(login = username, navController = navController, viewModel = viewModel)
                }
            }

        composable(Routes.Profile.route) { navBackStackEntry ->
            val username = navBackStackEntry.arguments?.getString("username")
            ProfileScreen( login = username, navController = navController, viewModel = viewModel)
            }

        composable(Routes.Favorites.route) {
            FavoritesScreen()
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF000099)
@Composable
fun MainScr() {
    GitFollowersTheme {

    }
}