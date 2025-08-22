package com.ashwinrao.packup.navigation

import kotlinx.serialization.Serializable

sealed interface NavRoute {

    @Serializable
    object MainScreen : NavRoute

    @Serializable
    object CameraScreen : NavRoute
}