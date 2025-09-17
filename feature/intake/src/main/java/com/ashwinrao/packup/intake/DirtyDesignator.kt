/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake

import com.ashwinrao.packup.intake.model.IntakeField
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Designates fields that have received input from the user as "dirty".
 * This can be used as a precondition to performing field validation - only
 * fields which have received input (dirty) can have validation run on them.
 */
@ViewModelScoped
class DirtyDesignator @Inject constructor() {
    private var dirtyFields = MutableStateFlow<Set<IntakeField>>(emptySet())

    val areRequiredFieldsDirty: Flow<Boolean> =
        check(IntakeField.REQUIRED)

    val isNameFieldDirty: Flow<Boolean> =
        check(IntakeField.Name)

    val isDescriptionFieldDirty: Flow<Boolean> =
        check(IntakeField.Description)

    fun designateDirty(vararg fields: IntakeField) {
        dirtyFields.value = dirtyFields.value + fields
    }

    private fun check(field: IntakeField): Flow<Boolean> = dirtyFields.map { it.contains(field) }

    private fun check(fields: Collection<IntakeField>): Flow<Boolean> = dirtyFields.map { it.containsAll(fields) }
}
