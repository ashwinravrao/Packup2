/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 *
 * Permission is NOT granted to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of this software, in whole or in part, except
 * with the author's prior written permission.
 *
 * This software is provided "AS IS", without warranty of any kind.
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.ashwinrao.packup.feature.main.ui.composable.Dashboard
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

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SearchBar(
                state = searchBarState,
                results = allItems, // originally `emptyList()`, todo: replace with filtering logic
                isExpanded = isSearchBarExpanded,
                onExpanded = { isSearchBarExpanded = it },
                onTextSearch = { /* ? */ },
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
        AnimatedVisibility(
            visible = !isSearchBarExpanded,
            enter = fadeIn(animationSpec = tween(durationMillis = 300)),
            exit = fadeOut(animationSpec = tween(durationMillis = 150)),
        ) {
            Dashboard(
                modifier = Modifier.padding(padding),
                items = allItems,
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
