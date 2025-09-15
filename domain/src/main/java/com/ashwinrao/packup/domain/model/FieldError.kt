/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.domain.model

sealed interface FieldError {
    data object RequiredButAbsent : FieldError

    companion object {
        val allErrors: List<FieldError>
            get() = listOf(RequiredButAbsent)
    }
}
