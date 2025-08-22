package com.ashwinrao.packup.feature.camera.viewmodel

import android.content.Context
import androidx.lifecycle.LifecycleOwner

class FakeCameraViewModel(): CameraViewModel {
    override fun init(context: Context, lifecycleOwner: LifecycleOwner) {}
    override fun startCamera() {}
    override fun stopCamera() {}
    override fun captureImage() {}
    override fun arePermissionsGranted() {}
}
