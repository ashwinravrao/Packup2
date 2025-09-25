/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
 */

package com.ashwinrao.packup.domain.model

sealed interface FieldError {
    data object RequiredButAbsent : FieldError

    companion object {
        val allErrors: List<FieldError>
            get() = listOf(RequiredButAbsent)
    }
}
