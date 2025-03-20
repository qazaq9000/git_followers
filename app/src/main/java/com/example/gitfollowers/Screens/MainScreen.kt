package com.example.gitfollowers.Screens

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.gitfollowers.Navigation.Routes
import com.example.gitfollowers.R
import com.example.gitfollowers.ViewModel.AppViewModel
import com.example.gitfollowers.ui.theme.GitFollowersTheme

@Composable
fun MainScreen(
    navController: NavHostController
) {
    var username by rememberSaveable { mutableStateOf("idimetrix") }

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
                onClick = {
                    navController.navigate(Routes.Result.createRoute(username)) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                },
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




    @Preview(showBackground = true, backgroundColor = 0xFF000099)
    @Composable
    fun MainScr() {
        GitFollowersTheme {

        }
    }