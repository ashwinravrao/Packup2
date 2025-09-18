/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.feature.common.composable

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7_PRO
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ashwinrao.packup.feature.common.R
import com.ashwinrao.packup.feature.common.theme.PackupTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PermissionExplanation(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @StringRes buttonText: Int,
    @StringRes explanation: Int,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { _ ->
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 64.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                textAlign = TextAlign.Justify,
                text = stringResource(id = explanation),
            )
            Button(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                onClick = onClick,
            ) {
                Text(text = stringResource(id = buttonText))
            }
        }
    }
}

@Preview(device = PIXEL_7_PRO, showSystemUi = true)
@Composable
private fun PermissionExplanationPreview() {
    PackupTheme {
        PermissionExplanation(
            explanation = R.string.permission_explanation_preview_message,
            buttonText = R.string.permission_explanation_preview_button,
            onClick = {},
        )
    }
}
