/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake.composable

import android.R.attr.value
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ashwinrao.packup.domain.model.IntakeError
import com.ashwinrao.packup.feature.common.theme.PackupTheme
import com.ashwinrao.packup.intake.IntakeScreen
import com.ashwinrao.packup.intake.viewmodel.IntakeScreenViewModel

@Composable
fun IntakeScreenContent(modifier: Modifier = Modifier, viewmodel: IntakeScreenViewModel, previewImageUri: Uri?) {
    val nameFieldValue by viewmodel.nameField.collectAsStateWithLifecycle()
    val descriptionFieldValue by viewmodel.descriptionField.collectAsStateWithLifecycle()

    val nameValidation by viewmodel.nameValidation.collectAsStateWithLifecycle()
    val descriptionValidation by viewmodel.descriptionValidation.collectAsStateWithLifecycle()

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

        Text(
            modifier = Modifier
                .padding(start = 24.dp, top = 48.dp, bottom = 8.dp),
            text = "Tell us about your item",
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 13.sp),
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth(),
            label = { Text(text = "Name *") },
            shape = RoundedCornerShape(8.dp),
            trailingIcon = {
                if (nameFieldValue.text.isNotBlank()) {
                    IconButton(
                        onClick = {
                            viewmodel.updateName(TextFieldValue())
                        },
                    ) {
                        Icon(
                            modifier = Modifier.scale(0.75f),
                            imageVector = Icons.Filled.Close,
                            contentDescription = "escape button",
                        )
                    }
                }
            },
            isError = nameValidation?.error is IntakeError.RequiredField,
            value = nameFieldValue,
            onValueChange = viewmodel::updateName,
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                .fillMaxWidth(),
            label = { Text(text = "Description *") },
            shape = RoundedCornerShape(8.dp),
            minLines = 4,
            maxLines = 4,
            isError = descriptionValidation?.error is IntakeError.RequiredField,
            value = descriptionFieldValue,
            onValueChange = viewmodel::updateDescription,
        )
    }
}

@Preview
@Composable
private fun IntakeScreenContentPreview() {
    PackupTheme {
        Scaffold { contentPadding ->
            IntakeScreen(
                modifier =
                Modifier
                    .padding(contentPadding)
                    .fillMaxSize(),
                itemImageUri = null,
                onEscape = {},
            )
        }
    }
}
