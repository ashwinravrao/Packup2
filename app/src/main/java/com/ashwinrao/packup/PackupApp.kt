/*
 * Copyright (c) 2025 Ashwin Rao (github:ashwinravrao).
 *
 * This file is part of Packup 2.
 *
 * Packup 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Packup 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
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
