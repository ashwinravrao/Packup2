package com.ashwinrao.packup.feature.camera.viewmodel

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.ashwinrao.packup.domain.camera.CameraManager
import com.ashwinrao.packup.feature.camera.manager.CameraManagerFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RealCameraViewModel @Inject constructor(
    private val factory: CameraManagerFactory
) : ViewModel(), CameraViewModel {

    lateinit var manager: CameraManager
        private set

    override fun init(context: Context, lifecycleOwner: LifecycleOwner) {
        manager = factory.create(context, lifecycleOwner)
    }

    override fun startCamera() {
        manager.startCamera()
    }

    override fun stopCamera() {
        manager.stopCamera()
    }

    override fun captureImage() {
        manager.captureImage()
    }

    override fun arePermissionsGranted() {
        manager.arePermissionsGranted()
    }
}