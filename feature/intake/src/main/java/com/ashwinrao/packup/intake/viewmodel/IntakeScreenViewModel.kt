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
import kotlinx.coroutines.flow.StateFlow

interface IntakeScreenViewModel {
    val currentItem: StateFlow<Item?>

    val selectedName: StateFlow<TextFieldValue>

    val validatedName: StateFlow<ValidatedFieldInput>

    val selectedDescription: StateFlow<TextFieldValue>

    val validatedDescription: StateFlow<ValidatedFieldInput>

    val isFormValid: StateFlow<Boolean>

    fun updateName(new: TextFieldValue)

    fun updateDescription(new: TextFieldValue)

    fun fetchCurrentItem(id: Long)

    fun discardCurrentItem()

    fun saveCurrentItem()

    fun startItemDraft(imageUri: Uri?)
}
