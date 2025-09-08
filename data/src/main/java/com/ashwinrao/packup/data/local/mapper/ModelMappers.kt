// Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.

package com.ashwinrao.packup.data.local.mapper

import com.ashwinrao.packup.data.local.model.ItemEntity
import com.ashwinrao.packup.domain.model.Item

fun ItemEntity.toDomainModel(): Item = Item(
    id = id,
    name = name,
    photoUri = photoUri,
    description = description,
    locationType = locationType,
)

fun Item.toDataEntity(): ItemEntity = ItemEntity(
    id = id,
    name = name,
    photoUri = photoUri,
    description = description,
    locationType = locationType,
)
