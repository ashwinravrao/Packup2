package com.ashwinrao.packup.intake

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7_PRO
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ashwinrao.packup.feature.common.theme.PackupTheme
import com.ashwinrao.packup.intake.composable.ItemImagePreview

@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun IntakeScreen(
    modifier: Modifier = Modifier,
    itemImageUri: Uri?,
    onExit: () -> Unit,
) {
    val context = LocalContext.current

    // lock orientation until i can support landscape, tbd
    DisposableEffect(Unit) {
        val activity = context as? Activity
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            ItemImagePreview(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                uri = itemImageUri
            )
        }
    }
}

@Composable
@Preview(device = PIXEL_7_PRO, showSystemUi = true)
fun IntakeScreenPreview() {
    PackupTheme {
        IntakeScreen(
            itemImageUri = null,
            onExit = {}
        )
    }
}