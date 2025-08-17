package com.ashwinrao.packup

import androidx.compose.runtime.Composable
import com.ashwinrao.packup.navigation.PackupNavHost
import com.ashwinrao.packup.ui.theme.PackupTheme

@Composable
fun PackupApp() {
    // any composition local providers would go here and wrap the following
    PackupTheme { PackupNavHost() }
}