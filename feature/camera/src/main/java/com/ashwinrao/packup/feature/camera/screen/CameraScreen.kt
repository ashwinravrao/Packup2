package com.ashwinrao.packup.feature.camera.screen

import android.Manifest
import android.annotation.SuppressLint
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7_PRO
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.ashwinrao.packup.feature.common.theme.PackupTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    onComplete: () -> Unit,
) {
    val cameraPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.CAMERA,
        )
    )
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController: LifecycleCameraController =
        remember { LifecycleCameraController(context) }

    LaunchedEffect(Unit) {
        cameraController.bindToLifecycle(lifecycleOwner)
    }

    when {
        cameraPermissionState.allPermissionsGranted -> {
            Scaffold(
                modifier = modifier.fillMaxSize()
            ) { innerPadding ->
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

        cameraPermissionState.shouldShowRationale -> {
            // todo: add a more elaborate message with a placeholder and button/instructions
            //  to show the user how to enable permissions
            Text("Camera permission is needed to use this feature.")
        }

        else -> {
            Text("Requesting camera permission...")  // todo: determine if necessary
            LaunchedEffect(Unit) {
                cameraPermissionState.launchMultiplePermissionRequest()
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HandleSinglePermissionRequest(
    requiredPermission: String,
    onRequested: @Composable () -> Unit,
    onGranted: @Composable () -> Unit,
    onTemporarilyDenied: @Composable () -> Unit,
    onPermanentlyDenied: @Composable () -> Unit,
) {
    val state = rememberPermissionState(requiredPermission)
    val hasRequestedBefore = rememberSaveable { mutableStateOf(false) }

    val isGranted = state.status.isGranted
    val isTempDenied = state.status.shouldShowRationale
    val isPermaDenied = hasRequestedBefore.value && !isGranted && !isTempDenied

    when {
        isGranted -> onGranted()
        isPermaDenied -> onPermanentlyDenied()
        isTempDenied -> onTemporarilyDenied()
        else -> {
            onRequested() // first time only
            LaunchedEffect(Unit) {
                if (!hasRequestedBefore.value) {
                    hasRequestedBefore.value = true
                    state.launchPermissionRequest()
                }
            }
        }
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
