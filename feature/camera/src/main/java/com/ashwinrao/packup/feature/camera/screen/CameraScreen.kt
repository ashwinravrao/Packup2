package com.ashwinrao.packup.feature.camera.screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Environment.DIRECTORY_PICTURES
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.CameraController.IMAGE_CAPTURE
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.ashwinrao.packup.feature.camera.R
import com.ashwinrao.packup.feature.camera.composable.CaptureButton
import com.ashwinrao.packup.feature.common.composable.PermissionExplanation
import com.ashwinrao.packup.feature.common.composable.RequestPermission
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.delay
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    onSuccess: (uri: Uri?) -> Unit,
    onFailure: (error: String?) -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraController: LifecycleCameraController = remember {
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
            CameraScreenPlaceholder(modifier = modifier)
        },
        onGranted = {
            CameraScreenContent(
                modifier = modifier,
                cameraController = cameraController,
                onCapturePhoto = {
                    takePhoto(
                        file = createFile(context),
                        context = context,
                        onSuccess = onSuccess,
                        onFailure = { onFailure(it.message) },
                        cameraController = cameraController,
                    )
                }
            )
        },
        onSoftDenied = { onRetry ->
            PermissionExplanation(
                modifier = modifier,
                explanation = R.string.camera_permission_explanation_soft_denial,
                buttonTitle = R.string.camera_permission_button_title_soft_denial,
                action = onRetry
            )
        },
        onHardDenied = { onGoToSettings ->
            PermissionExplanation(
                modifier = modifier,
                explanation = R.string.camera_permission_explanation_hard_denial,
                buttonTitle = R.string.camera_permission_button_title_hard_denial,
                action = onGoToSettings
            )
        }
    )
}

private fun takePhoto(
    file: File,
    context: Context,
    onSuccess: (uri: Uri?) -> Unit,
    onFailure: (e: ImageCaptureException) -> Unit,
    cameraController: LifecycleCameraController,
) {
    val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()
    cameraController.takePicture(
        outputFileOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                onSuccess(outputFileResults.savedUri)
            }

            override fun onError(exception: ImageCaptureException) {
                onFailure(exception)
            }
        }
    )
}

private fun createFile(context: Context): File {
    val dir = context.getExternalFilesDir(DIRECTORY_PICTURES) ?: context.filesDir
    if (!dir.exists()) dir.mkdirs()

    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val filename = "IMG_$timeStamp.jpg"

    return File(dir, filename)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun CameraScreenPlaceholder(
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { _ ->
        // any content added here should be covered up by the permission dialog
        // but if for some reason, the dialog doesn't appear, maybe we want to display
        // a placeholder image, text, and toggle? TBD
    }
}

@Composable
private fun CameraScreenContent(
    modifier: Modifier = Modifier,
    cameraController: LifecycleCameraController,
    onCapturePhoto: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(color = Color.Black)
        )
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f),
            factory = { context ->
                PreviewView(context).apply {
                    controller = cameraController
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .background(color = Color.Black),
            contentAlignment = Alignment.Center
        ) {
            CaptureButton(
                modifier = Modifier.padding(bottom = 32.dp),
                onClick = onCapturePhoto,
            )
        }
    }
}
