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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.ashwinrao.packup.feature.camera.R
import com.ashwinrao.packup.feature.camera.composable.CaptureButton
import com.ashwinrao.packup.feature.camera.model.CameraScreenContentMode
import com.ashwinrao.packup.feature.common.theme.PackupTheme
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

@Composable
fun PhotoPreviewButtons(
    modifier: Modifier = Modifier,
    onSave: () -> Unit,
    onRetake: () -> Unit,
) {
    Row(
        modifier = modifier
            .wrapContentWidth()
            .height(100.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.error,
            ),
            shape = RoundedCornerShape(8.dp),
            onClick = onRetake
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        painter = painterResource(R.drawable.ic_retake),
                        contentDescription = "retake button"
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .padding(top = 8.dp),
                        text = "RETAKE",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(32.dp))
        Card(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
            shape = RoundedCornerShape(8.dp),
            onClick = onSave
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        painter = painterResource(R.drawable.ic_floppy_disk),
                        contentDescription = "save button"
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .padding(top = 8.dp),
                        text = "SAVE",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun Preview() {
    PackupTheme(darkTheme = true) {
        PhotoPreviewButtons(
            onSave = {},
            onRetake = {}
        )
    }
}
