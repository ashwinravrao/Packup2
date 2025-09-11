/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake.viewmodel

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.intake.model.IntakeFormErrors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeIntakeScreenViewModel : IntakeScreenViewModel {
    override val currentItem: StateFlow<Item?> = MutableStateFlow(null)
    override val nameField: StateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    override val descriptionField: StateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    override val formErrors: StateFlow<IntakeFormErrors> = MutableStateFlow(IntakeFormErrors())

    override fun updateName(new: TextFieldValue) {}

    override fun updateDescription(new: TextFieldValue) {}

    override fun fetchCurrentItem(id: Long) {}

    override fun discardCurrentItem() {}

    override fun saveCurrentItem() {}

    override fun newItemAsDraft(uri: Uri?) {}
}
