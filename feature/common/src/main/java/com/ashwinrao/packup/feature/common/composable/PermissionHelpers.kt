/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.feature.common.composable

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission(
    permission: String,
    onGranted: @Composable () -> Unit,
    onRequested: @Composable () -> Unit,
    onSoftDenied: @Composable (retry: () -> Unit) -> Unit,
    onHardDenied: @Composable (openSettings: () -> Unit) -> Unit,
) {
    val context = LocalContext.current
    val state = rememberPermissionState(permission)
    val isFirstRequest = rememberSaveable { mutableStateOf(false) }
    val requestAgain = rememberSaveable { mutableStateOf(false) }

    // Request permission for the first time
    LaunchedEffect(Unit) {
        state.launchPermissionRequest()
        isFirstRequest.value = true
    }

    // Restart request when returning from settings
    LifecycleResumeEffect(requestAgain) {
        if (requestAgain.value) {
            state.launchPermissionRequest()
            requestAgain.value = false
        }
        onPauseOrDispose {}
    }

    val isGranted = state.status.isGranted
    val isSoftDenied = state.status.shouldShowRationale
    val isHardDenied = !isFirstRequest.value && !isGranted && !isSoftDenied

    when {
        isGranted -> {
            onGranted()
            isFirstRequest.value = false
        }

        isSoftDenied -> {
            onSoftDenied { state.launchPermissionRequest() }
            isFirstRequest.value = false
        }

        isHardDenied ->
            onHardDenied {
                val wereSettingsLaunched = openAppInAndroidSettings(context)
                requestAgain.value = wereSettingsLaunched
            }

        else -> onRequested()
    }
}

private fun openAppInAndroidSettings(context: Context): Boolean {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null),
    )
    context.startActivity(intent)
    return true
}
