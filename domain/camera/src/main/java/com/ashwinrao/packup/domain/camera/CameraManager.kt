package com.ashwinrao.packup.domain.camera

interface CameraManager {
    fun startCamera(): Boolean
    fun stopCamera(): Boolean
    fun arePermissionsGranted(): Boolean
    fun captureImage(): Boolean
}