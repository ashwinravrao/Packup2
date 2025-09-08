// Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.

package com.ashwinrao.packup.intake.composable

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ashwinrao.packup.feature.common.theme.PackupTheme

@Composable
fun IntakeScreenContent(modifier: Modifier = Modifier, previewImageUri: Uri?) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        ItemImagePreview(
            modifier =
            Modifier
                .fillMaxWidth(),
            uri = previewImageUri,
        )
    }
}

@Preview
@Composable
private fun IntakeScreenContentPreview() {
    PackupTheme {
        Scaffold { contentPadding ->
            IntakeScreenContent(
                modifier =
                Modifier
                    .padding(contentPadding)
                    .fillMaxSize(),
                previewImageUri = null,
            )
        }
    }
}
