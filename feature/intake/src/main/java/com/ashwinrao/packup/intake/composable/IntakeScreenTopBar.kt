/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake.composable

import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ashwinrao.packup.feature.common.theme.PackupTheme
import com.ashwinrao.packup.intake.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntakeScreenTopBar(
    modifier: Modifier = Modifier,
    isSaveEnabled: Boolean,
    onDiscard: () -> Unit,
    onSave: () -> Unit,
    onEscape: () -> Unit,
) {
    TopAppBar(
        modifier = modifier.padding(all = 8.dp),
        title = { /* no title desired */ },
        actions = {
            FilledIconButton(
                onClick = onDiscard,
                shape = CircleShape,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "discard button",
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onSave,
                contentPadding = ButtonDefaults.TextButtonContentPadding,
                enabled = isSaveEnabled,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_done_outlined),
                    contentDescription = "save button",
                )
                Spacer(Modifier.width(8.dp))
                Text("Save")
            }
        },
        navigationIcon = {
            IconButton(onClick = onEscape) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "escape button",
                )
            }
        },
    )
}

@Preview
@Composable
private fun IntakeScreenTopBarPreview() {
    PackupTheme {
        IntakeScreenTopBar(
            isSaveEnabled = true,
            onDiscard = {},
            onSave = {},
            onEscape = {},
        )
    }
}
