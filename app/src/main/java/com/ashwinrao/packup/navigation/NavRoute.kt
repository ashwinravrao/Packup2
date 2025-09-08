// Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.

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
