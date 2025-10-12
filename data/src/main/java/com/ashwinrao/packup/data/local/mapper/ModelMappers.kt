/*
 * Copyright (c) 2025 Ashwin Rao (github:ashwinravrao).
 *
 * Licensed under the GNU General Public License v3.0 or later (GPLv3+).
 * See the LICENSE file or <https://www.gnu.org/licenses/> for details.
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
)

fun Item.toDataModel(): ItemEntity = ItemEntity(
    id = if (id == null || id == 0L) null else id,
    name = name,
    imageUri = imageUri,
    description = description,
    state = state,
)
