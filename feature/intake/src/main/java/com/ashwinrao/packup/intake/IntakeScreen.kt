package com.ashwinrao.packup.intake

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7_PRO
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ashwinrao.packup.feature.common.theme.PackupTheme
import com.ashwinrao.packup.intake.composable.ItemImagePreview

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun IntakeScreen(
    modifier: Modifier = Modifier,
    itemImageUri: Uri?,
    onExit: () -> Unit,
) {
    val context = LocalContext.current

    // lock orientation until i can support landscape, tbd
    DisposableEffect(Unit) {
        val activity = context as? Activity
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(all = 8.dp),
                title = {},
                actions = {
                    FilledIconButton(
                        onClick = {
                            // todo: don't save item to storage
                            //  but delete captured photo using uri,
                            //  other tasks in the future vm
                        },
                        shape = CircleShape,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "delete button"
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            // todo: save item to storage via future vm
                        },
                        contentPadding = ButtonDefaults.TextButtonContentPadding,
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_done_outlined),
                            contentDescription = "save button"
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Done")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onExit) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "exit button"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Top
        ) {
            ItemImagePreview(
                modifier = Modifier
                    .fillMaxWidth(),
                uri = itemImageUri
            )
        }
    }
}

@Composable
@Preview(device = PIXEL_7_PRO, showSystemUi = true)
fun IntakeScreenPreview() {
    PackupTheme {
        IntakeScreen(
            itemImageUri = null,
            onExit = {},
        )
    }
}