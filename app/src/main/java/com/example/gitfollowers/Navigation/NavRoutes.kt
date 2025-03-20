package com.example.gitfollowers.Navigation


sealed class Routes(val route: String) {
    data object Main: Routes("main")
    data object Result: Routes("result/{username}"){
        fun createRoute(username: String?) = "result/$username"
    }
    data object Profile: Routes("profile/{username}"){
       fun createRoute(username: String?) = "profile/$username"
    }
    data object Favorites: Routes("favorites/{username}"){
    }

}