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
    requestRetryFlag: MutableState<Boolean>,
    requiredPermission: String,
    onGranted: @Composable () -> Unit,
    onSoftDenied: @Composable (onRetry: () -> Unit) -> Unit,
    onHardDenied: @Composable (onGoToSettings: () -> Unit) -> Unit,
) {
    val context = LocalContext.current
    val state = rememberPermissionState(requiredPermission)

    // Request permissions on first composition
    LaunchedEffect(Unit) {
        state.launchPermissionRequest()
    }

    // Runs when the app regains foreground (as is the case when returning from system settings).
    // Check if the retry flag has been set; if so, launch a new permissions request to advance in the flow.
    LifecycleResumeEffect(requestRetryFlag) {
        if (requestRetryFlag.value) {
            state.launchPermissionRequest()
            requestRetryFlag.value = false
        }
        onPauseOrDispose { }
    }

    if (state.status.isGranted) {
        onGranted()
    } else {
        if (state.status.shouldShowRationale) {
            // In a soft denial, the user can be persuaded to grant permissions with an explanation.
            onSoftDenied { state.launchPermissionRequest() }
        } else {
            // In a hard denial, the feature may be permanently inaccessible.
            // If the user wishes to access the feature, they must grant permission from system settings.
            // We can offer an explanation but must respect the user's choice to deny the permission.
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