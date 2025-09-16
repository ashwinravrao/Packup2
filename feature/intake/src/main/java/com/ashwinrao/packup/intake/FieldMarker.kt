/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake

import com.ashwinrao.packup.intake.model.IntakeField
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class FieldMarker @Inject constructor() {
    private var fields = mutableSetOf<IntakeField>()
    fun isFieldDirty(field: IntakeField) = field in fields
    fun markAsDirty(field: IntakeField) = fields.add(field)
}
