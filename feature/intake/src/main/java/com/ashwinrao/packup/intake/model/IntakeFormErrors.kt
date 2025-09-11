/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake.model

data class IntakeFormErrors(var nameField: IntakeUIError? = null, var descriptionField: IntakeUIError? = null) {
    val isNameValid: Boolean
        get() = nameField == null

    val isDescriptionValid: Boolean
        get() = descriptionField == null

    val isEverythingValid: Boolean
        get() = isNameValid && isDescriptionValid
}
