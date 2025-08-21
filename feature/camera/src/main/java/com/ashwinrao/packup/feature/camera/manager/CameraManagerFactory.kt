package com.ashwinrao.packup.feature.camera.manager

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.ashwinrao.packup.domain.camera.CameraManager

interface CameraManagerFactory {
    fun create(context: Context, lifecycleOwner: LifecycleOwner): CameraManager
}