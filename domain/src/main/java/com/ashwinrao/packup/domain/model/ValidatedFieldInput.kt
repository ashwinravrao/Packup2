/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.domain.model

data class ValidatedFieldInput(val input: String = "", val error: FieldError? = null) {
    val isError: Boolean
        get() = error in FieldError.allErrors
}
