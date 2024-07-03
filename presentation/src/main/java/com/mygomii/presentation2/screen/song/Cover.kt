package com.mygomii.presentation2.screen.song

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mygomii.presentation2.R

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
            .clip(CircleShape)
            .size(150.dp)
    )
}

@Composable
@Preview
fun CoverPreview() {
    Cover()
}

