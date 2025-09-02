package com.ashwinrao.packup.feature.camera.screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.ashwinrao.packup.feature.camera.composable.PhotoPreviewButtons
import com.ashwinrao.packup.feature.camera.model.CameraScreenContentMode
import com.ashwinrao.packup.util.io.saveBitmapToFile

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

    fun disposeOfImageProxy() {
        imageProxy?.close()
        imageProxy = null
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
                val rotatedBitmap = compensateForSensorRotation(proxy)
                PhotoPreviewContent(
                    bitmap = rotatedBitmap,
                    onSave = { bitmap ->
                        saveBitmapToFile(
                            context = context,
                            bitmap = rotatedBitmap,
                            onSuccess = { uri ->
                                disposeOfImageProxy()
                                onCapturePhoto(uri)
                                Log.d("CameraScreen", "Image proxy saved to file; uri = $uri")
                            },
                            onError = { error ->
                                disposeOfImageProxy()
                                Log.e(
                                    "CameraScreen",
                                    error.message ?: "Failed to save image proxy to file."
                                )
                            }
                        )
                    },
                    onRetake = {
                        disposeOfImageProxy()
                    }
                )
            }

        }
    }
}

private fun compensateForSensorRotation(proxy: ImageProxy): Bitmap {
    val ogBitmap = proxy.toBitmap()
    val rotationDegrees = proxy.imageInfo.rotationDegrees
    val rotatedBitmap = if (rotationDegrees != 0) {
        val matrix = Matrix()
        matrix.postRotate(rotationDegrees.toFloat())
        Bitmap.createBitmap(
            ogBitmap, 0, 0, ogBitmap.width, ogBitmap.height, matrix, true
        )
    } else {
        ogBitmap
    }
    return rotatedBitmap
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
    bitmap: Bitmap,
    onSave: (Bitmap) -> Unit,
    onRetake: () -> Unit,
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
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "preview of the captured image",
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f),
            alignment = Alignment.Center,
            contentScale = ContentScale.FillBounds,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(2f)
                .background(color = Color.Black),
        ) {
            PhotoPreviewButtons(
                modifier = Modifier.padding(32.dp),
                onSave = { onSave(bitmap) },
                onRetake = onRetake
            )
        }
    }
}
