// Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.

package com.ashwinrao.packup.data.local.typeconverter

import androidx.room.TypeConverter
import com.ashwinrao.packup.domain.model.ItemType

class ItemTypeConverter {
    @TypeConverter
    fun fromItemTypeToString(itemType: ItemType?): String? = when (itemType) {
        is ItemType.Draft -> "draft"
        is ItemType.Complete -> "complete"
        is ItemType.Corrupted -> "corrupted"
        null -> null
    }

    @TypeConverter
    fun fromStringToItemType(value: String?): ItemType? = when (value) {
        "draft" -> ItemType.Draft
        "complete" -> ItemType.Complete
        "corrupted" -> ItemType.Corrupted
        else -> null
    }
}
