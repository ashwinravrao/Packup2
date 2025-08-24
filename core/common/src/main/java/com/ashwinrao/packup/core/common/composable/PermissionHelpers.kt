package com.ashwinrao.packup.core.common.composable

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HandleSinglePermissionRequest(
    retryKey: MutableState<Boolean>,
    requiredPermission: String,
    onGranted: @Composable () -> Unit,
    onSoftDenied: @Composable (onRetry: () -> Unit) -> Unit,
    onHardDenied: @Composable (onGoToSettings: () -> Unit) -> Unit,
) {
    val context = LocalContext.current
    val state = rememberPermissionState(requiredPermission)

    LaunchedEffect(Unit) {
        state.launchPermissionRequest()
    }

    LifecycleResumeEffect(retryKey) {
        if (retryKey.value) {
            state.launchPermissionRequest()
            retryKey.value = false
        }
        onPauseOrDispose { }
    }

    if (state.status.isGranted) {
        onGranted()
    } else {
        if (state.status.shouldShowRationale) {
            onSoftDenied { state.launchPermissionRequest() }
        } else {
            onHardDenied { launchAppDetailsInSystemSettings(context) }
        }
    }
}

private fun launchAppDetailsInSystemSettings(context: Context) {
    val intent = android.content.Intent(
        android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        android.net.Uri.fromParts("package", context.packageName, null)
    )
    context.startActivity(intent)
}