/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.feature.main.ui.composable

import android.R.attr.bottom
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ashwinrao.packup.domain.model.Item

internal const val ITEM_GRID_COLUMNS = 2

@Composable
fun ItemList(modifier: Modifier = Modifier, items: List<Item>, onItemClick: (Item) -> Unit) {
    val lazyGridState = rememberLazyGridState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(ITEM_GRID_COLUMNS),
        modifier = modifier,
        state = lazyGridState,
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 8.dp),
    ) {
        items(items.size) { index ->
            ItemCard(item = items[index], index = index, onClick = onItemClick)
        }
    }
}
