/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake.viewmodel

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import com.ashwinrao.packup.domain.model.Item
import kotlinx.coroutines.flow.StateFlow

interface IntakeScreenViewModel {
    val currentItem: StateFlow<Item?>

    val nameField: StateFlow<TextFieldValue>

    val descriptionField: StateFlow<TextFieldValue>

    fun setNameField(new: TextFieldValue)

    fun setDescriptionField(new: TextFieldValue)

    fun fetchCurrentItem(id: Long)

    fun discardCurrentItem()

    fun saveCurrentItem()

    fun newItemAsDraft(uri: Uri?)
}
