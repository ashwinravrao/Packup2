/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
 */

package com.ashwinrao.packup.feature.camera.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ashwinrao.packup.feature.camera.R
import com.ashwinrao.packup.feature.common.theme.PackupTheme

@Composable
fun PhotoPreviewButtons(modifier: Modifier = Modifier, onSave: () -> Unit, onRetake: () -> Unit) {
    Row(
        modifier =
        modifier
            .wrapContentWidth()
            .height(100.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Card(
            modifier =
            Modifier
                .fillMaxSize()
                .weight(1f),
            colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.error,
            ),
            shape = RoundedCornerShape(8.dp),
            onClick = onRetake,
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier = Modifier.size(48.dp),
                ) {
                    Icon(
                        modifier =
                        Modifier
                            .fillMaxSize()
                            .weight(1f),
                        painter = painterResource(R.drawable.ic_retake),
                        contentDescription = stringResource(R.string.content_description_retake_button),
                    )
                    Text(
                        modifier =
                        Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .padding(top = 8.dp),
                        text = stringResource(R.string.retake_button_text),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(32.dp))
        Card(
            modifier =
            Modifier
                .fillMaxSize()
                .weight(1f),
            colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
            shape = RoundedCornerShape(8.dp),
            onClick = onSave,
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier = Modifier.size(48.dp),
                ) {
                    Icon(
                        modifier =
                        Modifier
                            .fillMaxSize()
                            .weight(1f),
                        painter = painterResource(R.drawable.ic_floppy_disk),
                        contentDescription = stringResource(R.string.content_description_save_button),
                    )
                    Text(
                        modifier =
                        Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .padding(top = 8.dp),
                        text = stringResource(R.string.save_button_text),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun PhotoPreviewButtonsPreview() {
    PackupTheme(darkTheme = true) {
        PhotoPreviewButtons(
            onSave = {},
            onRetake = {},
        )
    }
}
