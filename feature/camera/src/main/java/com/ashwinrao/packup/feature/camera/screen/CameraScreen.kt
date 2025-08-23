package com.ashwinrao.packup.feature.camera.screen

import android.Manifest
import android.annotation.SuppressLint
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7_PRO
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.ashwinrao.packup.core.common.composable.HandleSinglePermissionRequest
import com.ashwinrao.packup.feature.common.theme.PackupTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    onComplete: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraController: LifecycleCameraController = remember {
        LifecycleCameraController(context)
    }

    LaunchedEffect(Unit) {
        cameraController.bindToLifecycle(lifecycleOwner)
    }

    HandleSinglePermissionRequest(
        requiredPermission = Manifest.permission.CAMERA,
        onRequested = { /* request launched automatically; show optional progress ui here */ },
        onGranted = {
            CameraScreenContent(
                modifier = modifier,
                cameraController = cameraController
            )
        },
        onTemporarilyDenied = { onRetry ->
            // the user has denied the first request, but we can try again by doing the following
            // todo: show placeholder and text explaining why we need the permission
            // todo: add a button that calls onRetry, launching another permissions dialog
        },
        onPermanentlyDenied = { guideUserToSettings ->
            // the user has permanently denied the request and we can no longer retry
            // however, we can still explain to the user why the permission is critical to this feature
            // and hope they grant permission
            // todo: show placeholder and text explaining why the feature cannot be used without the permission being granted
            // todo: add a button that takes user to the app-specific system settings page
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun CameraScreenContent(
    modifier: Modifier = Modifier,
    cameraController: LifecycleCameraController,
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { _ ->
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                PreviewView(context).apply {
                    controller = cameraController
                }
            }
        )
    }
}

@Composable
@Preview(device = PIXEL_7_PRO, showSystemUi = true)
fun CameraScreenPreview() {
    PackupTheme {
        CameraScreen(
            onComplete = {}
        )
    }
}
