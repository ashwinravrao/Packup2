/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ashwinrao.packup.domain.model.CompositionState
import com.ashwinrao.packup.domain.model.Measurement

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val name: String?,
    val imageUri: String?,
    val description: String?,
    val state: CompositionState,
    val quantity: Int,
    val tags: List<String>,
    val measurements: List<Measurement>,
)
