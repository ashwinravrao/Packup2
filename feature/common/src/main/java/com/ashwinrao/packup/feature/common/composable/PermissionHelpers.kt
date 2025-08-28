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
fun HandleSinglePermissionRequest(
    requiredPermission: String,
    onRequestInFlight: @Composable () -> Unit,
    onGranted: @Composable () -> Unit,
    onSoftDenied: @Composable (onRetry: () -> Unit) -> Unit,
    onHardDenied: @Composable (onGoToSettings: () -> Unit) -> Unit,
) {
    val context = LocalContext.current
    val state = rememberPermissionState(requiredPermission)
    val isInitialRequestInFlight = rememberSaveable { mutableStateOf(false) }
    val retryPermissionRequest = rememberSaveable { mutableStateOf(false) }

    // Request permission on first composition
    LaunchedEffect(Unit) {
        state.launchPermissionRequest()
        isInitialRequestInFlight.value = true
    }

    // Retry permission request when user returns from app-specific system settings
    LifecycleResumeEffect(retryPermissionRequest) {
        if (retryPermissionRequest.value) {
            state.launchPermissionRequest()
            retryPermissionRequest.value = false
        }
        onPauseOrDispose { /* do nothing */ }
    }

    val isGranted = state.status.isGranted
    val isSoftDenied = state.status.shouldShowRationale
    val isHardDenied = !isInitialRequestInFlight.value && !isGranted && !isSoftDenied

    when {
        isGranted -> {
            onGranted()
            isInitialRequestInFlight.value = false
        }

        isSoftDenied -> {
            onSoftDenied { state.launchPermissionRequest() }
            isInitialRequestInFlight.value = false
        }

        isHardDenied -> onHardDenied {
            val launched = launchAppSpecificSystemSettings(context)
            if (launched) retryPermissionRequest.value = true
        }

        else -> onRequestInFlight()
    }
}

private fun launchAppSpecificSystemSettings(context: Context): Boolean {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null)
    )
    context.startActivity(intent)
    return true
}