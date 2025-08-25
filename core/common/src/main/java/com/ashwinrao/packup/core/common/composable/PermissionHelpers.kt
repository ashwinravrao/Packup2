package com.ashwinrao.packup.core.common.composable

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    onRequestInFlight: @Composable () -> Unit,
    onGranted: @Composable () -> Unit,
    onSoftDenied: @Composable (onRetry: () -> Unit) -> Unit,
    onHardDenied: @Composable (onGoToSettings: () -> Unit) -> Unit,
) {
    val context = LocalContext.current
    val state = rememberPermissionState(requiredPermission)
    val isInitialRequestInFlight = remember { mutableStateOf(false) }

    // Request permission on first composition
    LaunchedEffect(Unit) {
        state.launchPermissionRequest()
        isInitialRequestInFlight.value = true
    }

    // When lifecycle owner resumes && retry flag has been thrown,
    // initiate a new permission request to advance in the flow
    LifecycleResumeEffect(requestRetryFlag) {
        if (requestRetryFlag.value) {
            state.launchPermissionRequest()
            requestRetryFlag.value = false
        }
        onPauseOrDispose { }
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
        isHardDenied -> onHardDenied { launchAppDetailsInSystemSettings(context) }
        else -> onRequestInFlight()
    }
}

private fun launchAppDetailsInSystemSettings(context: Context) {
    val intent = android.content.Intent(
        android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        android.net.Uri.fromParts("package", context.packageName, null)
    )
    context.startActivity(intent)
}