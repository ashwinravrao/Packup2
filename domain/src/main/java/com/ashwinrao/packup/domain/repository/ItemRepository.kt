package com.ashwinrao.packup.domain.repository

import com.ashwinrao.packup.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    suspend fun getItems(): Flow<List<Item>>
    suspend fun getItem(id: Int): Item
    suspend fun packItem(item: Item)
    suspend fun discardItem(id: Int)
    suspend fun discardItems(ids: List<Int>)
}