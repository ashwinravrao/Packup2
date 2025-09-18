/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.feature.main.ui.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import com.ashwinrao.packup.feature.common.theme.PackupTheme
import com.ashwinrao.packup.feature.main.R
import com.ashwinrao.packup.feature.common.R as CommonR

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    isFilterActionEnabled: Boolean = true,
    isSortActionEnabled: Boolean = true,
    isSettingsActionEnabled: Boolean = true,
    onSettingsActionClicked: () -> Unit = {},
    onFilterActionClicked: () -> Unit = {},
    onSortActionClicked: () -> Unit = {},
    onCameraFabClicked: () -> Unit,
) {
    BottomAppBar(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        actions = {
            BottomBarAction(
                enabled = isSettingsActionEnabled,
                onActionClicked = onSettingsActionClicked,
                icon = R.drawable.ic_settings_outlined,
                contentDescription = CommonR.string.settings_button_description,
            )

            BottomBarAction(
                enabled = isFilterActionEnabled,
                onActionClicked = onFilterActionClicked,
                icon = R.drawable.ic_filter,
                contentDescription = R.string.action_filter_content_description,
            )

            BottomBarAction(
                enabled = isSortActionEnabled,
                onActionClicked = onSortActionClicked,
                icon = R.drawable.ic_sort,
                contentDescription = R.string.action_sort_content_description,
            )
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
                    painter = painterResource(CommonR.drawable.ic_camera),
                    contentDescription = stringResource(CommonR.string.camera_fab_button_description),
                )
            }
        },
    )
}

@Composable
private fun BottomBarAction(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onActionClicked: () -> Unit,
    @DrawableRes icon: Int,
    @StringRes contentDescription: Int,
) {
    if (enabled) {
        IconButton(
            modifier = modifier.padding(top = 8.dp, start = 10.dp),
            onClick = onActionClicked,
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = stringResource(contentDescription),
            )
        }
    }
}

@Composable
@Preview(device = PIXEL_7_PRO, showSystemUi = true)
private fun BottomNavBarPreview() {
    PackupTheme {
        BottomBar(
            onCameraFabClicked = {},
        )
    }
}
