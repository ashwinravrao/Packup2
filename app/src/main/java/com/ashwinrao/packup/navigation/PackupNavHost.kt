package com.ashwinrao.packup.navigation

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun PackupNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        navController = navController,
        startDestination = NavRoute.MainScreen,
    ) {
        mainScreen(
            onNavigateToCamera = {
                navController.navigate(route = NavRoute.CameraScreen)
            }
        )
        cameraScreen(
            onExit = { uri ->
                navController.navigate(
                    route = "${NavRoute.IntakeScreen}?imageUri=${Uri.encode(uri.toString())}"
                ) {
                    popUpTo(NavRoute.CameraScreen) { inclusive = true }
                }
            },
        )

        intakeScreen(
            onExit = {
                navController.popBackStack(NavRoute.MainScreen, inclusive = false)
            }
        )
    }
}
