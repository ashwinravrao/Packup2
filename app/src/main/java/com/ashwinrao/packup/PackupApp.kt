package com.ashwinrao.packup

import android.app.Application
import androidx.compose.runtime.Composable
import com.ashwinrao.packup.feature.common.theme.PackupTheme
import com.ashwinrao.packup.navigation.PackupNavHost
import dagger.hilt.android.HiltAndroidApp

@Composable
fun PackupApp() {
    // any composition local providers would go here and wrap the following
    PackupTheme { PackupNavHost() }
}

@HiltAndroidApp
class PackupApplication : Application()
