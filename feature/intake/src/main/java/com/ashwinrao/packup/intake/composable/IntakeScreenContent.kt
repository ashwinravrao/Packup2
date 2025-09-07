/*
 *  Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake.composable

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ashwinrao.packup.feature.common.theme.PackupTheme

@Composable
fun IntakeScreenContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    previewImageUri: Uri?,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding),
        verticalArrangement = Arrangement.Top
    ) {
        ItemImagePreview(
            modifier = Modifier
                .fillMaxWidth(),
            uri = previewImageUri
        )
    }
}

@Preview
@Composable
fun IntakeScreenContentPreview() {
    PackupTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            IntakeScreenContent(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                previewImageUri = null
            )
        }
    }
}