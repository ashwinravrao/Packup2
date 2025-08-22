package com.ashwinrao.packup.feature.camera.viewmodel

import com.ashwinrao.packup.feature.camera.model.CameraUIError
import kotlinx.coroutines.flow.SharedFlow

interface CameraViewModel {
    val errors: SharedFlow<CameraUIError>
}