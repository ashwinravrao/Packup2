/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
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

    private fun validateName(input: String): FieldError? =
        if (input.isBlank()) FieldError.RequiredButAbsent else null

    private fun validateDescription(input: String): FieldError? =
        if (input.isBlank()) FieldError.RequiredButAbsent else null
}
