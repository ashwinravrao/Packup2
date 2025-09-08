/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ashwinrao.packup.domain.model.ItemLocationType
import com.ashwinrao.packup.domain.model.ItemType

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 1L,
    val name: String?,
    val photoUri: String?,
    val description: String?,
    val locationType: ItemLocationType?,
    val itemType: ItemType?,
)
