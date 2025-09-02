package com.ashwinrao.packup.feature.main.ui.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7_PRO
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ashwinrao.packup.feature.common.R
import com.ashwinrao.packup.feature.common.theme.PackupTheme

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    onSettingsClicked: () -> Unit,
    onCameraFabClicked: () -> Unit,
) {
    BottomAppBar(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        actions = {
            IconButton(
                modifier = Modifier.padding(top = 8.dp),
                onClick = onSettingsClicked
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_settings),
                    contentDescription = stringResource(R.string.settings_button_description)
                )
            }
        },
        windowInsets = WindowInsets.navigationBars,
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(top = 8.dp),
                onClick = onCameraFabClicked,
                containerColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_camera),
                    contentDescription = stringResource(R.string.camera_fab_button_description)
                )
            }
        }
    )
}

@Composable
@Preview(device = PIXEL_7_PRO, showSystemUi = true)
fun BottomNavBarPreview() {
    PackupTheme {
        BottomNavBar(
            onSettingsClicked = {},
            onCameraFabClicked = {},
        )
    }
}