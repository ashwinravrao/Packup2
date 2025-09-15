/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake

import com.ashwinrao.packup.intake.model.IntakeField
import javax.inject.Inject

class FieldHygienist @Inject constructor() {
    private var fields = mutableSetOf<IntakeField>()
    fun isDirty(field: IntakeField) = field in fields
    fun markDirty(field: IntakeField) = fields.add(field)
}
