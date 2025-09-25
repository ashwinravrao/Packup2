/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
 */

package com.ashwinrao.packup.data.local.typeconverter

import androidx.room.TypeConverter
import com.ashwinrao.packup.domain.model.CompositionState

class CompositionStateConverter {
    @TypeConverter
    fun fromCompositionStateToString(compositionState: CompositionState?): String? = when (compositionState) {
        is CompositionState.Draft -> "draft"
        is CompositionState.Complete -> "complete"
        is CompositionState.Corrupted -> "corrupted"
        null -> null
    }

    @TypeConverter
    fun fromStringToCompositionState(value: String?): CompositionState? = when (value) {
        "draft" -> CompositionState.Draft
        "complete" -> CompositionState.Complete
        "corrupted" -> CompositionState.Corrupted
        else -> null
    }
}
