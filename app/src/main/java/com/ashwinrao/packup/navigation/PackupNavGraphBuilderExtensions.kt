package com.ashwinrao.packup.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ashwinrao.packup.feature.camera.screen.CameraScreen
import com.ashwinrao.packup.feature.main.ui.MainScreen

fun NavGraphBuilder.mainScreen(
    onNavigateToCamera: () -> Unit,
) {
    composable<NavRoute.MainScreen> { backstackEntry ->
        MainScreen(
            onNavigateToCamera = onNavigateToCamera
        )
    }
}

fun NavGraphBuilder.cameraScreen(
    onComplete: () -> Unit,
) {
    composable<NavRoute.CameraScreen> { backStackEntry ->
        CameraScreen(
            onComplete = onComplete
        )
    }
}
