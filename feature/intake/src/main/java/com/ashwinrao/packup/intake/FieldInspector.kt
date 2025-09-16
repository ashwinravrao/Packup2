/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake

import com.ashwinrao.packup.intake.model.IntakeField
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

/**
 * Tracks fields that have received input from the user (ie. rendered "dirty").
 */
@ViewModelScoped
class FieldInspector @Inject constructor() {
    private var fields = mutableSetOf<IntakeField>()

    fun areRequiredFieldsDirty(required: Collection<IntakeField>): Boolean =
        fields.containsAll(required)

    fun isDirty(field: IntakeField) = field in fields

    fun markAsDirty(field: IntakeField) = fields.add(field)
}
