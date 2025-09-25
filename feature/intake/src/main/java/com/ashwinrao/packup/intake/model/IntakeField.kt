/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
 */

package com.ashwinrao.packup.intake.model

enum class IntakeField {
    Name,
    Description,
    ;

    companion object {
        val REQUIRED: Set<IntakeField> = setOf(Name, Description)
    }
}
