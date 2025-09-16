/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake

import com.ashwinrao.packup.domain.model.FormInputField
import com.ashwinrao.packup.intake.model.IntakeField

fun IntakeField.toDomain() = when (this) {
        IntakeField.Name -> FormInputField.Name
        IntakeField.Description -> FormInputField.Description
    }
