/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
 */

package com.ashwinrao.packup.intake.composable

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ashwinrao.packup.domain.model.ValidatedFieldInput
import com.ashwinrao.packup.feature.common.theme.PackupTheme
import com.ashwinrao.packup.intake.IntakeScreen
import com.ashwinrao.packup.intake.R
import com.ashwinrao.packup.intake.viewmodel.IntakeScreenViewModel

@Composable
fun IntakeScreenContent(
    modifier: Modifier = Modifier,
    viewmodel: IntakeScreenViewModel,
    previewImageUri: Uri?,
) {

    val selectedName by viewmodel.selectedName.collectAsStateWithLifecycle()
    val selectedDescription by viewmodel.selectedDescription.collectAsStateWithLifecycle()

    val validatedName by viewmodel.validatedName.collectAsStateWithLifecycle()
    val validatedDescription by viewmodel.validatedDescription.collectAsStateWithLifecycle()

    val isNameFieldDirty by viewmodel.isNameFieldDirty.collectAsStateWithLifecycle()
    val isDescriptionFieldDirty by viewmodel.isDescriptionFieldDirty.collectAsStateWithLifecycle()

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

        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 24.dp, end = 24.dp, bottom = 16.dp, top = 36.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Describe your item...",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 13.sp),
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.padding(start = 30.dp),
                painter = painterResource(R.drawable.ic_id_card),
                contentDescription = "",
                tint = getIconTintForValidation(validatedName, isNameFieldDirty),
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .weight(1f),
                label = { Text(text = stringResource(R.string.hint_name_field)) },
                shape = RoundedCornerShape(8.dp),
                trailingIcon = {
                    if (selectedName.text.isNotBlank()) {
                        IconButton(
                            onClick = {
                                viewmodel.updateName(TextFieldValue())
                            },
                        ) {
                            Icon(
                                modifier = Modifier.scale(0.75f),
                                imageVector = Icons.Filled.Close,
                                contentDescription = stringResource(R.string.content_description_name_field_clear_button),
                            )
                        }
                    }
                },
                isError = !validatedName.isValid,
                value = selectedName,
                onValueChange = viewmodel::updateName,
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.padding(start = 30.dp),
                painter = painterResource(R.drawable.ic_description),
                contentDescription = "",
                tint = getIconTintForValidation(validatedDescription, isDescriptionFieldDirty),
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .weight(1f),
                label = { Text(text = stringResource(R.string.hint_description_field)) },
                shape = RoundedCornerShape(8.dp),
                minLines = 4,
                maxLines = 4,
                isError = !validatedDescription.isValid,
                value = selectedDescription,
                onValueChange = viewmodel::updateDescription,
            )
        }
    }
}

@Composable
private fun getIconTintForValidation(input: ValidatedFieldInput, isFieldDirty: Boolean): Color {
    if (isFieldDirty) {
        return if (input.isValid) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.error
    }
    return Color.Unspecified
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
