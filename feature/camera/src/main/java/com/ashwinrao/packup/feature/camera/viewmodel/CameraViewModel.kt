package com.ashwinrao.packup.feature.camera.viewmodel

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.ashwinrao.packup.domain.camera.CameraManager
import com.ashwinrao.packup.feature.camera.manager.CameraManagerFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val factory: CameraManagerFactory
) : ViewModel() {

    lateinit var manager: CameraManager
        private set

    fun init(context: Context, lifecycleOwner: LifecycleOwner) {
        manager = factory.create(context, lifecycleOwner)
    }

    fun startCamera() {
        manager.startCamera()
    }

    fun stopCamera() {
        manager.stopCamera()
    }

    fun captureImage() {
        manager.captureImage()
    }

    fun arePermissionsGranted() {
        manager.arePermissionsGranted()
    }
}