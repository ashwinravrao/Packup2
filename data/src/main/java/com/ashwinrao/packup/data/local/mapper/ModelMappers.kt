/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
 */

package com.ashwinrao.packup.data.local.mapper

import com.ashwinrao.packup.data.local.model.ItemEntity
import com.ashwinrao.packup.domain.model.Item

fun ItemEntity.toDomainModel(): Item = Item(
    id = id,
    name = name,
    imageUri = imageUri,
    description = description,
    state = state,
    quantity = quantity,
    tags = tags,
    measurements = measurements,
)

fun Item.toDataModel(): ItemEntity = ItemEntity(
    id = if (id == null || id == 0L) null else id,
    name = name,
    imageUri = imageUri,
    description = description,
    state = state,
    quantity = quantity,
    tags = tags,
    measurements = measurements,
)
