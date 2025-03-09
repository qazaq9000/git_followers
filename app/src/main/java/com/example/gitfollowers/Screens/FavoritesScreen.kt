package com.example.gitfollowers.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gitfollowers.R
import com.example.gitfollowers.ui.theme.GitFollowersTheme

@Composable
fun FavoritesScreen(){

    Column(
        horizontalAlignment = Alignment.Start,

        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
            .padding(start = 8.dp)
    ){
        Text("Favorites",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(8.dp))
      /*  LazyColumn(
            modifier = Modifier,
        ) {
            items(){ profile ->
                User(name = profile.username, img = profile.image, modifier = Modifier)
            }
        }*/

    }
}

@Composable
fun User(modifier: Modifier = Modifier, name: String, img: Int){

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Image(painter = painterResource(img),
            contentDescription = null,

            modifier = Modifier
                .size(100.dp)
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
        )

        Text(text = name,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier
                .weight(2f)
                .padding(start = 8.dp)
        )
        IconButton(
            onClick = {},
            enabled = true,
            modifier = Modifier.weight(0.5f)
        ){
            Icon(
                painter = painterResource(R.drawable.baseline_star_border_24),
                contentDescription = null,
                tint = Color.Yellow,
                modifier = Modifier.size(50.dp)
            )
        }

        IconButton(
            onClick = {},
            modifier = Modifier.weight(0.5f)
        ){
            Icon(
                painter = painterResource(R.drawable.baseline_arrow_forward_ios_24),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
            )
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun FavoritesPreview(){
    GitFollowersTheme {

    }
}
