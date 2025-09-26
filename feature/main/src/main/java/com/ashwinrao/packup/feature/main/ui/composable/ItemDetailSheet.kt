/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
 */

package com.ashwinrao.packup.feature.main.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.feature.main.R
import com.ashwinrao.packup.feature.main.ui.viewmodel.ItemSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailSheet(
    modifier: Modifier = Modifier,
    item: ItemSelection,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (item is ItemSelection.Selected) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = onDismiss,
            sheetState = sheetState,
        ) {
            ItemDetailSheet(
                modifier = Modifier.fillMaxHeight(0.90f),
                item = item.item,
            )
        }
    }
}

@Composable
fun ItemDetailSheet(modifier: Modifier = Modifier, item: Item) {
    Box(modifier = modifier.fillMaxWidth()) {
        AsyncImage(
            model =
                ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUri)
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
                .align(Alignment.TopStart)
                .wrapContentSize()
                .padding(12.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardColors(
                contentColor = Color.Black.copy(alpha = 0.5f),
                containerColor = Color.Black.copy(alpha = 0.5f),
                disabledContainerColor = Color.Black.copy(alpha = 0.5f),
                disabledContentColor = Color.Black.copy(alpha = 0.5f),
            ),
        ) {
            Column(modifier = Modifier.padding(32.dp)) {
                Text(text = "id=${item.id}", color = Color.White)
                Text(text = "name=${item.name}", color = Color.White)
                Text(text = "description=${item.description}", color = Color.White)
                Text(text = "quantity=${item.quantity}", color = Color.White)
                Text(text = "tags=${item.tags}", color = Color.White)
                Text(text = "measurements=${item.measurements}", color = Color.White)
                Text(text = "state=${item.state}", color = Color.White)
            }
        }
    }
}
