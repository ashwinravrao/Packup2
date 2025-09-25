/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
 */

package com.ashwinrao.packup.navigation

import kotlinx.serialization.Serializable

sealed interface NavRoute {
    @Serializable
    object MainScreen : NavRoute

    @Serializable
    object CameraScreen : NavRoute

    @Serializable
    object IntakeScreen : NavRoute
}
