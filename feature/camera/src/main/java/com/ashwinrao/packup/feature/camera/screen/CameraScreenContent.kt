package com.ashwinrao.packup.feature.camera.screen

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.ashwinrao.packup.feature.camera.composable.CaptureButton
import com.ashwinrao.packup.feature.camera.model.CameraScreenContentMode
import com.ashwinrao.packup.util.io.saveImageProxyToFile

@Composable
fun CameraScreenContent(
    modifier: Modifier = Modifier,
    cameraController: LifecycleCameraController,
    onCapturePhoto: (Uri?) -> Unit,
) {
    val context = LocalContext.current
    var imageProxy by remember { mutableStateOf<ImageProxy?>(null) }
    var contentMode by remember {
        mutableStateOf(CameraScreenContentMode.ViewFinder)
    }

    LaunchedEffect(imageProxy) {
        contentMode = if (imageProxy == null) {
            CameraScreenContentMode.ViewFinder
        } else {
            CameraScreenContentMode.PhotoPreview
        }
    }

    when (contentMode) {
        CameraScreenContentMode.ViewFinder -> {
            ViewFinderContent(
                modifier = modifier,
                context = context,
                cameraController = cameraController,
                onCaptured = { imageProxy = it },
                onCaptureFailed = {
                    // todo: log and display error
                }
            )
        }

        CameraScreenContentMode.PhotoPreview -> {
            imageProxy?.let { proxy ->
                PhotoPreviewContent(
                    imageProxy = proxy,
                    onSave = { bitmap ->
                        saveImageProxyToFile(
                            context = context,
                            bitmap = bitmap,
                            onSuccess = { uri ->
                                onCapturePhoto(uri)
                                Log.d("CameraScreen", "Image proxy saved to file; uri = $uri")
                            },
                            onError = { error ->
                                Log.e(
                                    "CameraScreen",
                                    error.message ?: "Failed to save image proxy to file."
                                )
                            }
                        )
                        proxy.close()
                        imageProxy = null
                    },
                    onRetake = {
                        proxy.close()
                        imageProxy = null
                    }
                )
            }

        }
    }
}

@Composable
private fun ViewFinderContent(
    modifier: Modifier = Modifier,
    context: Context,
    cameraController: LifecycleCameraController,
    onCaptured: (ImageProxy) -> Unit,
    onCaptureFailed: (ImageCaptureException) -> Unit,
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
                onClick = {
                    cameraController.takePicture(
                        ContextCompat.getMainExecutor(context),
                        object : ImageCapture.OnImageCapturedCallback() {
                            override fun onCaptureSuccess(image: ImageProxy) {
                                onCaptured(image)
                            }

                            override fun onError(exception: ImageCaptureException) {
                                onCaptureFailed(exception)
                            }
                        }
                    )
                },
            )
        }
    }
}

@Composable
private fun PhotoPreviewContent(
    modifier: Modifier = Modifier,
    imageProxy: ImageProxy,
    onSave: (Bitmap) -> Unit,
    onRetake: () -> Unit,
) {
    val preview = remember(imageProxy) {
        imageProxy.toBitmap()
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(color = Color.Black)
        )
        Image(
            bitmap = preview.asImageBitmap(),
            contentDescription = "preview of the captured image",
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f),
            contentScale = ContentScale.Fit,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .background(color = Color.Black),
        ) {
            Row {
                Button(
                    modifier = Modifier,
                    onClick = { onRetake() },
                ) {
                    Text("Retake")
                }
                Button(
                    modifier = Modifier,
                    onClick = { onSave(preview) },
                ) {
                    Text("Save")
                }
            }
        }
    }
}
