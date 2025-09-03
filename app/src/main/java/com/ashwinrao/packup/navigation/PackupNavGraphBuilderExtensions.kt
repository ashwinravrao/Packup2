package com.ashwinrao.packup.navigation

import android.net.Uri
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ashwinrao.packup.feature.camera.screen.CameraScreen
import com.ashwinrao.packup.feature.main.ui.MainScreen

fun NavGraphBuilder.mainScreen(
    onNavigateToCamera: () -> Unit,
) {
    composable<NavRoute.MainScreen> {
        MainScreen(
            onNavigateToCamera = onNavigateToCamera
        )
    }
}

fun NavGraphBuilder.cameraScreen(
    onSuccess: (uri: Uri?) -> Unit,
    onFailure: (error: String?) -> Unit,
) {
    composable<NavRoute.CameraScreen>(
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(
                    durationMillis = 400,
                    easing = EaseOutQuart
                )
            )
        },
        popExitTransition = {
            fadeOut(
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(300)
            )
        }
    ) {
        CameraScreen(
            onSuccess = onSuccess,
            onFailure = onFailure,
        )
    }
}
