/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake.viewmodel

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.intake.model.IntakeFormErrors
import kotlinx.coroutines.flow.StateFlow

interface IntakeScreenViewModel {
    val currentItem: StateFlow<Item?>

    val nameField: StateFlow<TextFieldValue>

    val descriptionField: StateFlow<TextFieldValue>

    val formErrors: StateFlow<IntakeFormErrors>

    fun updateName(new: TextFieldValue)

    fun updateDescription(new: TextFieldValue)

    fun fetchCurrentItem(id: Long)

    fun discardCurrentItem()

    fun saveCurrentItem()

    fun newItemAsDraft(uri: Uri?)
}
