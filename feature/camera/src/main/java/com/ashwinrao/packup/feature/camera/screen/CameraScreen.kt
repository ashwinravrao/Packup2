package com.ashwinrao.packup.feature.camera.screen

import android.Manifest
import android.annotation.SuppressLint
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
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

    val permissionRequestRetryKey = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        cameraController.bindToLifecycle(lifecycleOwner)
    }

    HandleSinglePermissionRequest(
        retryKey = permissionRequestRetryKey,
        requiredPermission = Manifest.permission.CAMERA,
        onGranted = {
            CameraScreenContent(
                modifier = modifier,
                cameraController = cameraController
            )
        },
        onSoftDenied = { onRetry ->
            Scaffold(
                modifier = modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("The camera permission is required")
                    Button(onClick = onRetry) {
                        Text("Grant Permission")
                    }
                }
            }
        },
        onHardDenied = { onGoToSettings ->
            Scaffold(
                modifier = modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("The camera permission is required to use this feature. Please enable it in system settings.")
                    Button(
                        onClick = {
                            onGoToSettings()
                            permissionRequestRetryKey.value = true
                        }
                    ) {
                        Text("Go To Settings")
                    }
                }
            }
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
