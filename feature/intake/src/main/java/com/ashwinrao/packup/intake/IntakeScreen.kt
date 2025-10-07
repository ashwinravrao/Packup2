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

package com.ashwinrao.packup.intake

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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
import com.ashwinrao.packup.intake.composable.IntakeTopBar
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

    LaunchedEffect(itemImageUri) {
        viewmodel.startItemDraft(itemImageUri)
    }

    val isFormValid by viewmodel.isFormValid.collectAsStateWithLifecycle()

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
            IntakeTopBar(
                modifier = Modifier.fillMaxWidth(),
                isSaveEnabled = isFormValid,
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
            modifier = Modifier
                .padding(contentPadding)
                .consumeWindowInsets(contentPadding),
            previewImageUri = itemImageUri,
            viewmodel = viewmodel,
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
