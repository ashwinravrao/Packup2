package com.ashwinrao.packup.feature.camera.manager

import android.content.Context
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.lifecycle.LifecycleOwner
import com.ashwinrao.packup.domain.camera.CameraManager

internal class DefaultCameraManager(
    context: Context,
    private val lifecycleOwner: LifecycleOwner
) : CameraManager {

    private val cameraController: CameraController =
        LifecycleCameraController(context)

    override fun startCamera(): Boolean {
        // todo: set up use cases
        return (cameraController as? LifecycleCameraController)?.bindToLifecycle(lifecycleOwner) != null
    }

    override fun stopCamera(): Boolean {
        TODO("Not yet implemented")
    }

    override fun arePermissionsGranted(): Boolean {
        TODO("Not yet implemented")
    }

    override fun captureImage(): Boolean {
        TODO("Not yet implemented")
    }
}