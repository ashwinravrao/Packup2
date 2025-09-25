/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
 */

package com.ashwinrao.packup.domain.model

data class ValidatedFieldInput(val input: String = "", val error: FieldError? = null) {
    val isValid: Boolean
        get() = error == null
}
