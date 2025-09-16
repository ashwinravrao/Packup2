/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake.model

enum class IntakeField {
    Name,
    Description;

    companion object {
        val REQUIRED: Set<IntakeField> = setOf(Name, Description)
    }
}
