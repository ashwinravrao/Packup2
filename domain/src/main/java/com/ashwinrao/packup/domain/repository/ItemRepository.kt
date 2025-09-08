/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.domain.repository

import com.ashwinrao.packup.domain.model.Item

interface ItemRepository {
    suspend fun getItems(): List<Item>

    suspend fun getItem(id: Long): Item

    suspend fun saveItem(item: Item): Long

    suspend fun discardItem(id: Long)

    suspend fun discardItems(ids: List<Long>)
}
