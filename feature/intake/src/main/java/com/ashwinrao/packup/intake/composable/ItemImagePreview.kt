/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake.composable

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7_PRO
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ashwinrao.packup.feature.common.theme.PackupTheme
import com.ashwinrao.packup.intake.R

@Composable
fun ItemImagePreview(modifier: Modifier = Modifier, uri: Uri? = null) {
    Card(
        modifier =
        modifier
            .wrapContentWidth()
            .padding(horizontal = 8.dp)
            .height(100.dp),
        shape =
        RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomStart = 8.dp,
            bottomEnd = 8.dp,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp, focusedElevation = 64.dp),
    ) {
        if (uri != null) {
            AsyncImage(
                model =
                ImageRequest.Builder(LocalContext.current)
                    .data(uri)
                    .crossfade(true)
                    .build(),
                contentDescription = "Captured Item Image",
                contentScale = ContentScale.Crop,
                modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3f),
            )
        } else {
            Box(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3f)
                    .padding(32.dp),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_coat_stand),
                    contentDescription = "Placeholder",
                    tint = Color.Gray,
                )
            }
        }
    }
}

@Composable
@Preview(device = PIXEL_7_PRO, showSystemUi = true)
private fun ItemImagePreviewPreview() {
    PackupTheme {
        ItemImagePreview()
    }
}
