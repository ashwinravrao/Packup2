package com.ashwinrao.packup.feature.camera.model

import androidx.annotation.StringRes
import com.ashwinrao.packup.feature.camera.R

sealed interface CameraUIError {
    @get:StringRes
    val stringRes: Int

    data object Initialization : CameraUIError {
        override val stringRes: Int
            get() = R.string.camera_ui_error_initialization
    }
    data object PermissionsDenied : CameraUIError {
        override val stringRes: Int
            get() = R.string.camera_ui_error_denied_permissions
    }
    data object ImageCaptureFailed : CameraUIError {
        override val stringRes: Int
            get() = R.string.camera_ui_error_image_capture_failed
    }
    data class Unknown(val exception: Exception) : CameraUIError {
        override val stringRes: Int
            get() = R.string.camera_ui_error_unknown
    }
}