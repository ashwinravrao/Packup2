package com.ashwinrao.packup.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ashwinrao.packup.domain.model.ItemLocationType

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey val id: Int,
    val name: String?,
    val photoUri: String,
    val description: String?,
    val locationType: ItemLocationType,
)