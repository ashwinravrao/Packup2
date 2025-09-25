/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.feature.main.ui.composable

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.feature.main.R

@Composable
fun ItemCard(modifier: Modifier = Modifier, index: Int, item: Item, onClick: (Item) -> Unit = {}) {
    val startPadding = if (index % 2 == 0) 0.dp else 8.dp
    val endPadding = if (index % 2 == 0) 8.dp else 0.dp

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(start = startPadding, end = endPadding, top = 8.dp, bottom = 8.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = { onClick(item) },
    ) {
        val decodedUri = item.imageUri?.let { Uri.decode(it) }?.toUri()
        Log.d("ItemCard", "decodedUri=$decodedUri")

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            AsyncImage(
                model =
                    ImageRequest.Builder(LocalContext.current)
                        .data(decodedUri)
                        .crossfade(true)
                        .scale(Scale.FILL)
                        .build(),
                contentDescription = stringResource(R.string.thumbnail_image),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier.matchParentSize(),
            )

            Card(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .wrapContentSize()
                    .padding(12.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardColors(
                    contentColor = Color.Black,
                    containerColor = Color.Black,
                    disabledContainerColor = Color.Black,
                    disabledContentColor = Color.Black,
                ),
            ) {
                val text = item.name?.take(50) ?: "" // todo: consider lifting
                Text(
                    text = text,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                )
            }
        }
    }
}
