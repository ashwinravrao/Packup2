package com.ashwinrao.packup.core.common.composable

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HandleSinglePermissionRequest(
    requiredPermission: String,
    onGranted: @Composable () -> Unit,
    onTemporarilyDenied: @Composable (() -> Unit) -> Unit,
    onPermanentlyDenied: @Composable (() -> Unit) -> Unit,
) {
    val context = LocalContext.current
    val state = rememberPermissionState(requiredPermission)
    val hasRequestedBefore = rememberSaveable { mutableStateOf(false) }

    val isGranted = state.status.isGranted
    val isTempDenied = state.status.shouldShowRationale
    val isPermaDenied = hasRequestedBefore.value && !isGranted && !isTempDenied

    when {
        isGranted -> onGranted()
        isPermaDenied -> onPermanentlyDenied { guideUserToSystemSettings(context) }
        isTempDenied -> onTemporarilyDenied { state.launchPermissionRequest() }
        else -> {
            LaunchedEffect(Unit) {
                if (!hasRequestedBefore.value) {
                    hasRequestedBefore.value = true
                    state.launchPermissionRequest()
                }
            }
        }
    }
}

private fun guideUserToSystemSettings(context: Context) {
    val intent = android.content.Intent(
        android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        android.net.Uri.fromParts("package", context.packageName, null)
    )
    context.startActivity(intent)
}