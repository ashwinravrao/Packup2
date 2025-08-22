package com.ashwinrao.packup.feature.camera.viewmodel

import android.content.Context
import androidx.lifecycle.LifecycleOwner

interface CameraViewModel {
    fun init(context: Context, lifecycleOwner: LifecycleOwner)
    fun startCamera()
    fun stopCamera()
    fun captureImage()
    fun arePermissionsGranted()
}