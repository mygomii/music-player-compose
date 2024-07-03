package com.mygomii.presentation2.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mygomii.presentation2.R

@Composable
fun PlayerView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Cover()
        Title()
        Singer()

        ControlView()
    }
}

@Composable
fun Cover() {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://example.com/image.jpg")
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.cover_sample),
        contentDescription = "sample",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(12.dp)
            .clip(CircleShape)
            .size(200.dp)
    )
}

@Composable
fun Title(title: String = "Lover") {
    Text(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 0.dp),
        text = title,
        fontSize = 20.sp,
        color = Color.White
    )
}

@Composable
fun Singer(singer: String = "Tayer Swift") {
    Text(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
        text = singer,
        fontSize = 18.sp,
        color = Color.Gray
    )
}

@Composable
fun Heart() {
    Icon(
        imageVector = Icons.Sharp.Favorite,
        tint = Color.White,
        contentDescription = "",
        modifier = Modifier
            .size(18.dp)

    )
}

@Composable
fun ControlView() {
    Row() {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            tint = Color.White,
            contentDescription = "back",
            modifier = Modifier
                .padding()
                .size(32.dp)
                .align(Alignment.CenterVertically)

        )
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            tint = Color.White,
            contentDescription = "play",
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .size(60.dp)
                .align(Alignment.CenterVertically)

        )
        Icon(
            imageVector = Icons.Default.ArrowForward,
            tint = Color.White,
            contentDescription = "next",
            modifier = Modifier
                .padding()
                .size(32.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Preview
@Composable
fun PreviewCover() {
    PlayerView()
}