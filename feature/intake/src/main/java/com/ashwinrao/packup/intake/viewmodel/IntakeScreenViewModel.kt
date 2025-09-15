/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake.viewmodel

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.domain.model.ValidatedFieldInput
import kotlinx.coroutines.flow.StateFlow

interface IntakeScreenViewModel {
    val currentItem: StateFlow<Item?>

    val selectedName: StateFlow<TextFieldValue>

    val validatedName: StateFlow<ValidatedFieldInput>

    val selectedDescription: StateFlow<TextFieldValue>

    val validatedDescription: StateFlow<ValidatedFieldInput>

    fun updateName(new: TextFieldValue)

    fun updateDescription(new: TextFieldValue)

    fun fetchCurrentItem(id: Long)

    fun discardCurrentItem()

    fun saveCurrentItem()

    fun newItemAsDraft(uri: Uri?)
}
