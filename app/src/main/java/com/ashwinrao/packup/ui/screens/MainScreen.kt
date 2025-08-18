package com.ashwinrao.packup.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7_PRO
import androidx.compose.ui.tooling.preview.Preview
import com.ashwinrao.packup.R
import com.ashwinrao.packup.ui.composables.TopSearchBar
import com.ashwinrao.packup.ui.theme.PackupTheme

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    val searchBarTextFieldState: TextFieldState = remember { TextFieldState() }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopSearchBar(
                textFieldState = searchBarTextFieldState,
                searchResults = emptyList(),
                onVoiceSearchToggled = { /* TODO: Figure out how to take in voice input */ },
                onTextSearched = { /* TODO: Retrieve item from DB/list and display a bottom sheet modal */ },
                hint = stringResource(R.string.hint_main_screen_top_search_bar)
            )
        }
    ) {  innerPadding ->
        // todo: add content
    }
}

@Composable
@Preview(device = PIXEL_7_PRO, showSystemUi = true)
fun MainScreenPreview() {
    PackupTheme {
        MainScreen()
    }
}
