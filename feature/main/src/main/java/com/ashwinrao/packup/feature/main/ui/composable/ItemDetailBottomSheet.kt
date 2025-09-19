/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.feature.main.ui.composable

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.feature.main.R
import com.ashwinrao.packup.feature.main.ui.viewmodel.ItemSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailBottomSheet(modifier: Modifier = Modifier, item: ItemSelection, onDismiss: () -> Unit = {}) {
    val sheetState = rememberModalBottomSheetState()
    if (item is ItemSelection.Selected) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = onDismiss,
            sheetState = sheetState,
        ) {
            ItemDetailBottomSheetContent(item = item.item)
        }
    }
}

@Composable
fun ItemDetailBottomSheetContent(modifier: Modifier = Modifier, item: Item) {
    val decodedUri = item.photoUri?.let { Uri.decode(it) }?.toUri()
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
    }
}
