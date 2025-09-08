/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 *
 * Permission is NOT granted to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of this software, in whole or in part, except
 * with the author's prior written permission.
 *
 * This software is provided "AS IS", without warranty of any kind.
 */

package com.ashwinrao.packup.intake

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7_PRO
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ashwinrao.packup.feature.common.composable.getViewModelForInspectionMode
import com.ashwinrao.packup.feature.common.theme.PackupTheme
import com.ashwinrao.packup.intake.composable.IntakeScreenContent
import com.ashwinrao.packup.intake.composable.IntakeScreenTopBar
import com.ashwinrao.packup.intake.viewmodel.FakeIntakeScreenViewModel
import com.ashwinrao.packup.intake.viewmodel.RealIntakeScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun IntakeScreen(modifier: Modifier = Modifier, itemImageUri: Uri?, onEscape: () -> Unit) {
    val context = LocalContext.current

    val viewmodel =
        getViewModelForInspectionMode(
            fake = FakeIntakeScreenViewModel(),
            real = { hiltViewModel<RealIntakeScreenViewModel>() },
        )

    val currentItem by viewmodel.currentItem.collectAsStateWithLifecycle()

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
        topBar = {
            IntakeScreenTopBar(
                onDiscard = {
                    viewmodel.discardCurrentItem()
                    onEscape()
                },
                onSave = {
                    viewmodel.saveCurrentItem()
                    onEscape()
                },
                onEscape = onEscape,
            )
        },
    ) { contentPadding ->
        IntakeScreenContent(
            modifier = Modifier.padding(contentPadding),
            previewImageUri = itemImageUri,
        )
    }
}

@Composable
@Preview(device = PIXEL_7_PRO, showSystemUi = true)
private fun IntakeScreenPreview() {
    PackupTheme {
        IntakeScreen(
            itemImageUri = null,
            onEscape = {},
        )
    }
}
