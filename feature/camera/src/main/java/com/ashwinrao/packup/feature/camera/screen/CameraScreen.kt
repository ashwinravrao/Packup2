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

package com.ashwinrao.packup.feature.camera.screen

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import androidx.camera.view.CameraController.IMAGE_CAPTURE
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.ashwinrao.packup.feature.camera.R
import com.ashwinrao.packup.feature.common.composable.PermissionExplanation
import com.ashwinrao.packup.feature.common.composable.RequestPermission
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.delay

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CameraScreen(modifier: Modifier = Modifier, onExit: (uri: Uri?) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val controller: LifecycleCameraController =
        remember { LifecycleCameraController(context) }

    LaunchedEffect(Unit) {
        delay(350) // wait til nav transition completes
        controller.setEnabledUseCases(IMAGE_CAPTURE)
        controller.bindToLifecycle(lifecycleOwner)
    }

    DisposableEffect(controller) {
        onDispose { controller.unbind() }
    }

    RequestPermission(
        permission = Manifest.permission.CAMERA,
        onRequested = {
            Surface(
                modifier = modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
                content = { /* any content will be covered by a system permissions dialog */ },
            )
        },
        onGranted = {
            CameraScreenContent(
                modifier = modifier,
                controller = controller,
                onCapturePhoto = onExit,
            )
        },
        onSoftDenied = { retry ->
            PermissionExplanation(
                modifier = modifier,
                onClick = retry,
                buttonText = R.string.camera_permission_button_title_soft_denial,
                explanation = R.string.camera_permission_explanation_soft_denial,
            )
        },
        onHardDenied = { openSettings ->
            PermissionExplanation(
                modifier = modifier,
                onClick = openSettings,
                buttonText = R.string.camera_permission_button_title_hard_denial,
                explanation = R.string.camera_permission_explanation_hard_denial,
            )
        },
    )
}
