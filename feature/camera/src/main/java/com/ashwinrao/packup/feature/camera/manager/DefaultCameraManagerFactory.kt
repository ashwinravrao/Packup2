package com.ashwinrao.packup.feature.camera.manager

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.ashwinrao.packup.domain.camera.CameraManager
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class DefaultCameraManagerFactory @Inject constructor(): CameraManagerFactory {
    override fun create(context: Context, lifecycleOwner: LifecycleOwner): CameraManager {
        return DefaultCameraManager(context, lifecycleOwner)
    }
}
