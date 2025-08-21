package com.ashwinrao.packup.di

import com.ashwinrao.packup.feature.camera.manager.CameraManagerFactory
import com.ashwinrao.packup.feature.camera.manager.DefaultCameraManagerFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class CameraModule {

    @Binds
    abstract fun bindCameraManagerFactory(
        impl: DefaultCameraManagerFactory
    ): CameraManagerFactory
}