package com.ashwinrao.packup.feature.camera.screen

import android.Manifest
import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7_PRO
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.ashwinrao.packup.core.common.composable.HandleSinglePermissionRequest
import com.ashwinrao.packup.feature.camera.R
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

    val onResumePermissionRequestRetryFlag = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        cameraController.bindToLifecycle(lifecycleOwner)
    }

    HandleSinglePermissionRequest(
        requestRetryFlag = onResumePermissionRequestRetryFlag,
        requiredPermission = Manifest.permission.CAMERA,
        onGranted = {
            CameraScreenContent(
                modifier = modifier,
                cameraController = cameraController
            )
        },
        onSoftDenied = { onRetry ->
            CameraPermissionExplanation(
                modifier = modifier,
                explanation = R.string.camera_permission_explanation_soft_denial,
                buttonTitle = R.string.camera_permission_button_title_soft_denial,
                action = onRetry
            )
        },
        onHardDenied = { onGoToSettings ->
            CameraPermissionExplanation(
                modifier = modifier,
                explanation = R.string.camera_permission_explanation_hard_denial,
                buttonTitle = R.string.camera_permission_button_title_hard_denial,
                action = {
                    onGoToSettings()
                    onResumePermissionRequestRetryFlag.value = true
                }
            )
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun CameraPermissionExplanation(
    modifier: Modifier,
    @StringRes explanation: Int,
    @StringRes buttonTitle: Int,
    action: () -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { _ ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 64.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                textAlign = TextAlign.Justify,
                text = stringResource(id = explanation)
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                onClick = action
            ) {
                Text(text = stringResource(id = buttonTitle))
            }
        }
    }
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
