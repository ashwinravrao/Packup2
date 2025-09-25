/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.data.local.repository

import com.ashwinrao.packup.data.local.mapper.toDataModel
import com.ashwinrao.packup.data.local.mapper.toDomainModel
import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.domain.repository.ItemRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalItemRepository
@Inject
constructor(private val itemDao: ItemDao) : ItemRepository {
    override fun getItems() = itemDao.getItems().map { entities ->
            entities.map { entity ->
                entity.toDomainModel()
            }
        }

    override suspend fun getItem(id: Long) = itemDao.getItem(id).toDomainModel()

    override suspend fun saveItem(item: Item): Long = itemDao.saveItem(item.toDataModel())

    override suspend fun discardItem(id: Long) = itemDao.discardItemById(id)

    override suspend fun discardItems(ids: List<Long>) {
        itemDao.discardItemsByIds(ids)
    }
}
