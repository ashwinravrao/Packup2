/*
 * Copyright (c) 2025 Ashwin Rao (github:ashwinravrao).
 *
 * Licensed under the GNU General Public License v3.0 or later (GPLv3+).
 * See the LICENSE file or <https://www.gnu.org/licenses/> for details.
 */

package com.ashwinrao.packup.domain.repository

import com.ashwinrao.packup.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getItems(): Flow<List<Item>>

    suspend fun getItem(id: Long): Item

    suspend fun saveItem(item: Item): Long

    suspend fun discardItem(id: Long)

    suspend fun discardItems(ids: List<Long>)
}
