/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.feature.main.ui.composable

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ashwinrao.packup.domain.model.Item

@Composable
fun Dashboard(modifier: Modifier = Modifier, items: List<Item>) {
    LazyColumn(modifier = modifier) {
        items(items.size) { index ->
            ItemCard(item = items[index])
        }
    }
}
