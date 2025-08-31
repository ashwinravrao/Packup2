package com.ashwinrao.packup.feature.main.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
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
import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.feature.common.R
import com.ashwinrao.packup.feature.common.theme.PackupTheme
import com.ashwinrao.packup.feature.main.ui.composables.Dashboard

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun MainScreen(
    modifier: Modifier = Modifier,
    onNavigateToCamera: () -> Unit,
) {
    val searchBarTextFieldState: TextFieldState = remember { TextFieldState() }
    var isSearchBarExpanded by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopSearchBar(
                textFieldState = searchBarTextFieldState,
                searchResults = emptyList(),
                isExpanded = isSearchBarExpanded,
                onExpanded = { isSearchBarExpanded = it },
                onVoiceSearchToggled = { /* TODO: Figure out how to take in voice input */ },
                onTextSearched = { /* TODO: Retrieve item from DB/list and display a bottom sheet modal */ },
                hint = stringResource(R.string.hint_main_screen_top_search_bar)
            )
        },
        bottomBar = {
            AnimatedVisibility(
                visible = !isSearchBarExpanded,
                enter = slideInVertically { it },
                exit = slideOutVertically { it },
            ) {
                BottomNavBar(
                    onSettingsClicked = { /* TODO: Figure out what view to open settings in (ie. bottom sheet? replace grid?) */ },
                    onCameraFabClicked = onNavigateToCamera,
                )
            }
        }
    ) { innerPadding ->
        Dashboard(
            modifier = Modifier.padding(innerPadding),
            items = Item.generated
        )
    }
}

@Composable
@Preview(device = PIXEL_7_PRO, showSystemUi = true)
fun MainScreenPreview() {
    PackupTheme {
        MainScreen(
            onNavigateToCamera = {}
        )
    }
}
