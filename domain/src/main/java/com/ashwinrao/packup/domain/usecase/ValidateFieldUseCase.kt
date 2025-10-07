/*
 * Copyright (c) 2025 Ashwin Rao (github:ashwinravrao).
 *
 * Licensed under the GNU General Public License v3.0 or later (GPLv3+).
 * See the LICENSE file or <https://www.gnu.org/licenses/> for details.
 */

package com.ashwinrao.packup.domain.usecase

import com.ashwinrao.packup.domain.model.FieldError
import com.ashwinrao.packup.domain.model.FormInputField
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ValidateFieldUseCase @Inject constructor() {
    operator fun invoke(input: String, field: FormInputField): FieldError? = when (field) {
        FormInputField.Name -> validateName(input)
        FormInputField.Description -> validateDescription(input)
    }

    private fun validateName(input: String): FieldError? = if (input.isBlank()) FieldError.RequiredButAbsent else null

    private fun validateDescription(input: String): FieldError? =
        if (input.isBlank()) FieldError.RequiredButAbsent else null
}
