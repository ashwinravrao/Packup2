/*
 * Copyright (c) 2025 Ashwin Rao (github:ashwinravrao).
 *
 * This file is part of Packup 2.
 *
 * Packup 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Packup 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.ashwinrao.packup.feature.main.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7_PRO
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ashwinrao.packup.feature.common.R
import com.ashwinrao.packup.feature.common.composable.getViewModelForInspectionMode
import com.ashwinrao.packup.feature.common.theme.PackupTheme
import com.ashwinrao.packup.feature.main.ui.composable.BottomBar
import com.ashwinrao.packup.feature.main.ui.composable.ItemDetailSheet
import com.ashwinrao.packup.feature.main.ui.composable.ItemList
import com.ashwinrao.packup.feature.main.ui.composable.SearchBar
import com.ashwinrao.packup.feature.main.ui.viewmodel.FakeMainScreenViewModel
import com.ashwinrao.packup.feature.main.ui.viewmodel.RealMainScreenViewModel

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun MainScreen(modifier: Modifier = Modifier, navigateToCamera: () -> Unit) {
    val searchBarState: TextFieldState = remember { TextFieldState() }
    var isSearchBarExpanded by rememberSaveable { mutableStateOf(false) }

    val viewModel = getViewModelForInspectionMode(
        fake = FakeMainScreenViewModel(),
        real = { hiltViewModel<RealMainScreenViewModel>() },
    )

    val allItems by viewModel.items.collectAsStateWithLifecycle()
    val filteredItems by viewModel.filteredItems.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedItem by viewModel.selectedItem.collectAsStateWithLifecycle()

    // Sync TextFieldState -> ViewModel for live search
    LaunchedEffect(searchBarState) {
        snapshotFlow { searchBarState.text.toString() }
            .collect { query ->
                viewModel.updateSearchQuery(query)
            }
    }

    // Clear TextFieldState when ViewModel clears search
    LaunchedEffect(searchQuery) {
        if (searchQuery.isEmpty() && searchBarState.text.isNotEmpty()) {
            searchBarState.clearText()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SearchBar(
                state = searchBarState,
                results = filteredItems,
                isExpanded = isSearchBarExpanded,
                onExpanded = { isSearchBarExpanded = it },
                onResultClick = { item ->
                    viewModel.collapseSearchBar()
                    isSearchBarExpanded = false
                    viewModel.selectItem(item)
                },
                hint = stringResource(R.string.hint_main_screen_top_search_bar),
            )
        },
        bottomBar = {
            AnimatedVisibility(
                visible = !isSearchBarExpanded,
                enter = slideInVertically { it },
                exit = slideOutVertically { it },
            ) {
                BottomBar(
                    onCameraClicked = navigateToCamera,
                )
            }
        },
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(
                visible = !isSearchBarExpanded,
                enter = fadeIn(animationSpec = tween(durationMillis = 300)),
                exit = fadeOut(animationSpec = tween(durationMillis = 150)),
            ) {
                ItemList(
                    modifier = Modifier.padding(padding),
                    items = allItems,
                    onItemClick = viewModel::selectItem,
                )
            }
            ItemDetailSheet(
                item = selectedItem,
                onDismiss = viewModel::unselectItem,
            )
        }
    }
}

@Composable
@Preview(device = PIXEL_7_PRO, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun MainScreenPreview() {
    PackupTheme {
        MainScreen(
            modifier = Modifier.fillMaxSize(),
            navigateToCamera = {},
        )
    }
}
