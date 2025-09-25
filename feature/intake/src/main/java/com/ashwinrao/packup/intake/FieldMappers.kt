/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
 */

package com.ashwinrao.packup.intake

import com.ashwinrao.packup.domain.model.FormInputField
import com.ashwinrao.packup.intake.model.IntakeField

fun IntakeField.toDomain() = when (this) {
        IntakeField.Name -> FormInputField.Name
        IntakeField.Description -> FormInputField.Description
    }
