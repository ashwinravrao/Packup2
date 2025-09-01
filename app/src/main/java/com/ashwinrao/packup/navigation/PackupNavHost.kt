package com.ashwinrao.packup.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun PackupNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
     NavHost(
         modifier = modifier,
         navController = navController,
         startDestination = NavRoute.MainScreen,
     ) {
         mainScreen {
             navController.navigate(route = NavRoute.CameraScreen)
         }
         cameraScreen(
             onSuccess = { uri ->
                 navController.popBackStack()
                 Log.d("PackupNavHost", "Photo saved - Uri: ${uri?.path}")
             },
             onFailure = { error ->
                 Log.e("PackupNavHost", "Failed to save photo - error: $error")
             }
         )
     }
}
