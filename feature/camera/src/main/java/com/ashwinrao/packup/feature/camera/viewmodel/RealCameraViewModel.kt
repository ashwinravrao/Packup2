package com.ashwinrao.packup.feature.camera.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashwinrao.packup.feature.camera.model.CameraUIError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealCameraViewModel @Inject constructor() : ViewModel(), CameraViewModel {

    private var _errors: MutableSharedFlow<CameraUIError> = MutableSharedFlow()
    override val errors: SharedFlow<CameraUIError> = _errors.asSharedFlow()

    private fun emitError(error: CameraUIError) {
        viewModelScope.launch {
            _errors.emit(error)
        }
    }
}