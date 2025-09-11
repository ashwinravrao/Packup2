/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake.model

import androidx.annotation.StringRes
import com.ashwinrao.packup.intake.R

sealed interface IntakeUIError {
    @get:StringRes
    val displayText: Int
    val field: IntakeField

    data class RequiredButEmpty(override val field: IntakeField) : IntakeUIError {
        override val displayText: Int
            get() = R.string.required_intake_error
    }
}
