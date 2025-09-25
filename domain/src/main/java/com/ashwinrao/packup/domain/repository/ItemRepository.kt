/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
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
