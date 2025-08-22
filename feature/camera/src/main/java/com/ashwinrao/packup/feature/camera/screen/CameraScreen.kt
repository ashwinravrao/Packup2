package com.ashwinrao.packup.feature.camera.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7_PRO
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.ashwinrao.packup.core.common.composable.getViewModelForInspectionMode
import com.ashwinrao.packup.feature.camera.viewmodel.CameraViewModel
import com.ashwinrao.packup.feature.camera.viewmodel.FakeCameraViewModel
import com.ashwinrao.packup.feature.camera.viewmodel.RealCameraViewModel
import com.ashwinrao.packup.feature.common.theme.PackupTheme

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun CameraScreen(
    modifier: Modifier = Modifier,
    onComplete: () -> Unit,
) {

    val cameraViewModel: CameraViewModel =
        getViewModelForInspectionMode(FakeCameraViewModel()) {
            hiltViewModel<RealCameraViewModel>()
        }

    cameraViewModel.init(
        context = LocalContext.current,
        lifecycleOwner = LocalLifecycleOwner.current
    )

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Text(text = "Hello world")
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
