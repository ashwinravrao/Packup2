/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.data.local.repository

import com.ashwinrao.packup.data.local.mapper.toDataEntity
import com.ashwinrao.packup.data.local.mapper.toDomainModel
import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.domain.repository.ItemRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalItemRepository
@Inject
constructor(private val itemDao: ItemDao) : ItemRepository {
    override suspend fun getItems(): List<Item> = itemDao.getItems().map { entities ->
        entities.toDomainModel()
    }

    override suspend fun getItem(id: Long) = itemDao.getItem(id).toDomainModel()

    override suspend fun saveItem(item: Item): Long = itemDao.saveItem(item.toDataEntity())

    override suspend fun discardItem(id: Long) = itemDao.discardItemById(id)

    override suspend fun discardItems(ids: List<Long>) {
        itemDao.discardItemsByIds(ids)
    }
}
