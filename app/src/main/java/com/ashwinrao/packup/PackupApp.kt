package com.ashwinrao.packup

import androidx.compose.runtime.Composable
import com.ashwinrao.packup.feature.common.theme.PackupTheme
import com.ashwinrao.packup.navigation.PackupNavHost

@Composable
fun PackupApp() {
    // any composition local providers would go here and wrap the following
    PackupTheme { PackupNavHost() }
}