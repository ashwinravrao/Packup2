package com.ashwinrao.packup.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7_PRO
import androidx.compose.ui.tooling.preview.Preview
import com.ashwinrao.packup.ui.theme.PackupTheme

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
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
