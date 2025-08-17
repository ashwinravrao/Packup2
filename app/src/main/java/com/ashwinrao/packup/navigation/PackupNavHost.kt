package com.ashwinrao.packup.navigation

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
         modifier = modifier,
         navController = navController,
         startDestination = NavRoute.MainScreen,
     ) {
         mainScreen()

         // todo: add other screens here
     }
}
