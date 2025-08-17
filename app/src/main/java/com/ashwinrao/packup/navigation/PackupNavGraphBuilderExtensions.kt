package com.ashwinrao.packup.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ashwinrao.packup.ui.screens.MainScreen

fun NavGraphBuilder.mainScreen() {
    composable<NavRoute.MainScreen> { backstackEntry ->
        MainScreen()
    }
}
