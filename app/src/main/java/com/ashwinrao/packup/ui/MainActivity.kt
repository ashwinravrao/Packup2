/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 *
 * Permission is NOT granted to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of this software, in whole or in part, except
 * with the author's prior written permission.
 *
 * This software is provided "AS IS", without warranty of any kind.
 */

package com.ashwinrao.packup.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ashwinrao.packup.PackupApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { PackupApp() }
    }
}
