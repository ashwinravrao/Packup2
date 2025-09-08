// Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.

package com.ashwinrao.packup.domain.repository

import com.ashwinrao.packup.domain.model.Item

interface ItemRepository {
    suspend fun getItems(): List<Item>

    suspend fun getItem(id: Int): Item

    suspend fun saveItem(item: Item)

    suspend fun discardItem(id: Int)

    suspend fun discardItems(ids: List<Int>)
}
