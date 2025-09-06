/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 *
 * Permission is NOT granted to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of this software, in whole or in part, except
 * with the author's prior written permission.
 *
 * This software is provided "AS IS", without warranty of any kind.
 */

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
