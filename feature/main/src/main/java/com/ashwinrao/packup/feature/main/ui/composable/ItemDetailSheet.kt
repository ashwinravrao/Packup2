/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
 */

package com.ashwinrao.packup.feature.main.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.feature.main.R
import com.ashwinrao.packup.feature.main.ui.viewmodel.ItemSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailSheet(modifier: Modifier = Modifier, item: ItemSelection, onDismiss: () -> Unit = {}) {
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
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
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
    }
}
