/*
 * Copyright (c) 2025 Ashwin Rao (github:ashwinravrao).
 *
 * Licensed under the GNU General Public License v3.0 or later (GPLv3+).
 * See the LICENSE file or <https://www.gnu.org/licenses/> for details.
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
