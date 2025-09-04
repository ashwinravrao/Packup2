package com.ashwinrao.packup.navigation

import android.net.Uri
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseInQuart
import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ashwinrao.packup.feature.camera.screen.CameraScreen
import com.ashwinrao.packup.feature.main.ui.MainScreen
import com.ashwinrao.packup.intake.IntakeScreen
import androidx.core.net.toUri

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
    onExit: (uri: Uri?) -> Unit,
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
        CameraScreen(onExit = onExit)
    }
}

fun NavGraphBuilder.intakeScreen(
    onExit: () -> Unit,
) {
    composable(
        route = "${NavRoute.IntakeScreen}?imageUri={imageUri}",
        arguments = listOf(
            navArgument("imageUri") {
                type = NavType.StringType
                nullable = true
            }
        ),
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(
                    durationMillis = 400,
                    easing = EaseInQuart
                )
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween()
            )
        },
        popExitTransition = {
            fadeOut(
                animationSpec = tween()
            )
        }
    ) { backStackEntry ->
        val uri = backStackEntry.arguments?.getString("imageUri")
        IntakeScreen(
            itemImageUri = uri?.toUri(),
            onExit = onExit,
        )
    }
}
