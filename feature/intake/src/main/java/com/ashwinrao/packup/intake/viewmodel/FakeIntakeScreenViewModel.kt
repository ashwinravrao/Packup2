/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
 */

package com.ashwinrao.packup.intake.viewmodel

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.domain.model.ValidatedFieldInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeIntakeScreenViewModel : IntakeScreenViewModel {
    override val currentItem: StateFlow<Item?> = MutableStateFlow(null)
    override val isNameFieldDirty: StateFlow<Boolean> = MutableStateFlow(false)
    override val isDescriptionFieldDirty: StateFlow<Boolean> = MutableStateFlow(false)
    override val selectedName: StateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    override val validatedName: StateFlow<ValidatedFieldInput> = MutableStateFlow(ValidatedFieldInput())
    override val selectedDescription: StateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    override val validatedDescription: StateFlow<ValidatedFieldInput> = MutableStateFlow(ValidatedFieldInput())
    override val isFormValid: StateFlow<Boolean> = MutableStateFlow(true)

    override fun updateName(new: TextFieldValue) {}

    override fun updateDescription(new: TextFieldValue) {}

    override fun fetchCurrentItem(id: Long) {}

    override fun discardCurrentItem() {}

    override fun saveCurrentItem() {}

    override fun startItemDraft(imageUri: Uri?) {}
}
