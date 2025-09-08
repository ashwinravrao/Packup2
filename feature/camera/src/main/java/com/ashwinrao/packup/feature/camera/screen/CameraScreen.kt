/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 *
 * Permission is NOT granted to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of this software, in whole or in part, except
 * with the author's prior written permission.
 *
 * This software is provided "AS IS", without warranty of any kind.
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

    val cameraController: LifecycleCameraController =
        remember {
            LifecycleCameraController(context)
        }

    LaunchedEffect(Unit) {
        delay(350) // wait til nav transition completes
        cameraController.setEnabledUseCases(IMAGE_CAPTURE)
        cameraController.bindToLifecycle(lifecycleOwner)
    }

    DisposableEffect(cameraController) {
        onDispose {
            cameraController.unbind()
        }
    }

    RequestPermission(
        permission = Manifest.permission.CAMERA,
        onRequested = {
            Surface(
                modifier = modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
                content = { /* intentional */ },
            )
        },
        onGranted = {
            CameraScreenContent(
                modifier = modifier,
                cameraController = cameraController,
                onCapturePhoto = onExit,
            )
        },
        onSoftDenied = { onRetry ->
            PermissionExplanation(
                modifier = modifier,
                explanation = R.string.camera_permission_explanation_soft_denial,
                buttonTitle = R.string.camera_permission_button_title_soft_denial,
                action = onRetry,
            )
        },
        onHardDenied = { onGoToSettings ->
            PermissionExplanation(
                modifier = modifier,
                explanation = R.string.camera_permission_explanation_hard_denial,
                buttonTitle = R.string.camera_permission_button_title_hard_denial,
                action = onGoToSettings,
            )
        },
    )
}
