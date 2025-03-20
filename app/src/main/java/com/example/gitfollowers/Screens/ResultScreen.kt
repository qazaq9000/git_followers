package com.example.gitfollowers.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil3.compose.AsyncImage
import com.example.gitfollowers.Navigation.Routes
import com.example.gitfollowers.ViewModel.AppViewModel
import com.example.gitfollowers.ui.theme.GitFollowersTheme


@Composable
fun ResultScreen(
    username: String,
    navController: NavController,
    viewModel: AppViewModel
) {

    val follower by viewModel.follower.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUserFollowers(username)
        viewModel.getUser(username)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Text(
            text = username,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Clip,
            modifier = Modifier.padding(16.dp)
        )

        TextField(
            value = "",
            label = { Text("Search for username") },
            onValueChange = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xff000000),
                focusedContainerColor = Color(0xff000000)
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Color.Black)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
        ) {
            items(follower) { item ->
                Follower(
                    username = item.login ?: "Unknown",
                    avatar = item.avatarUrl ?: "",
                    onClick = "${item.login}",
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun Follower(
    username: String,
    avatar: String,
    modifier: Modifier = Modifier,
    onClick: String,
    navController: NavController
) {

    Column(
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxSize()
            .clickable {
                navController.navigate(Routes.Profile.createRoute(onClick))
                },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            model = avatar,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Text(
            text = username,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            fontSize = 18.sp,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun ResultScreenPreview() {
    GitFollowersTheme {

//
    }
}