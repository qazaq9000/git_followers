package com.example.gitfollowers.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
fun ProfileScreen(
    username: String,
    navController: NavController,
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
        .verticalScroll(
            state = rememberScrollState(),
            enabled = true
        )
) {
    val user by viewModel.login.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUser(username)
        viewModel.getUserByLogin(username)
    }


    val list by viewModel.database.collectAsState()
    val isFavorite by derivedStateOf { list.any { it.id == user?.id } }
    val favoriteIcon = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
    val favoriteColor = if (isFavorite) Color.Red else Color.Gray


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF252627))
            .padding(8.dp)
    ) {
        ProfileInfo(
            img = user?.avatar ?: "",
            name = user?.name ?: "",
            username = user?.login ?: "",
            location = user?.location ?: "",
            onClick =
                {if (isFavorite) {
                user?.let { viewModel.deleteUser(it) }
            } else {
                user?.let { viewModel.addUser(it) }
            }},

            icon = favoriteIcon,
            tint = favoriteColor
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = user?.bio ?: "",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        InfoCard(
            repos = "Public Repos",
            reposCount = user?.repos ?: 0,
            gists = "Public Gists",
            gistsCount= user?.gists ?: 0,
            buttonText = "GitHub Profile",
            buttonColor = Color.Magenta,
            onClick = {}
        )

        InfoCard(
            repos = "Followers",
            reposCount = user?.followers ?: 0,
            gists = "Following",
            gistsCount= user?.following ?: 0,
            buttonText = "Get Followers",
            buttonColor = Color.Green,
            onClick = {navController.navigate(Routes.Result.createRoute(user?.login))}
        )

        Text("GitHub since ${user?.since?.substring(0,4)}",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun ProfileInfo(
    img: String,
    name: String,
    username: String,
    location: String,
    onClick:() -> Unit,
    icon: ImageVector,
    tint: Color
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {

        AsyncImage(
            model = img,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = username,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                softWrap = false,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = name,
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = location,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(30.dp)
                    .clickable{onClick()}
            )
        }
    }
}

@Composable
fun InfoCard(
    repos: String,
    reposCount: Int,
    gists: String,
    gistsCount: Int,
    buttonText: String,
    buttonColor: Color,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF282830))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Face,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                        Text(
                            text = repos,
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                    Text(
                        text = "$reposCount",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )

                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                        Text(
                            text = gists,
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                    Text(
                        text = "$gistsCount",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            }

            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = buttonText,
                    fontSize = 16.sp
                )
            }
        }
    }


}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun ProfileScreenPreview() {
    GitFollowersTheme {

    }
}
