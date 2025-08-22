package com.ashwinrao.packup.feature.camera.viewmodel

import com.ashwinrao.packup.feature.camera.model.CameraUIError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class FakeCameraViewModel(): CameraViewModel {
    override val errors: SharedFlow<CameraUIError> = MutableSharedFlow()
}
