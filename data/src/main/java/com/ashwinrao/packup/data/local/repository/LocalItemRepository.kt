package com.ashwinrao.packup.data.local.repository

import com.ashwinrao.packup.data.local.mapper.toDataEntity
import com.ashwinrao.packup.data.local.mapper.toDomainModel
import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalItemRepository @Inject constructor(
    private val itemDao: ItemDao
) : ItemRepository {

    override suspend fun getItems(): Flow<List<Item>> =
        itemDao.getItems().map { entities ->
            entities.map { entity ->
                entity.toDomainModel()
            }
        }

    override suspend fun getItem(id: Int) =
        itemDao.getItem(id).toDomainModel()

    override suspend fun packItem(item: Item) =
        itemDao.packItem(item.toDataEntity())

    override suspend fun discardItem(id: Int) =
        itemDao.discardItemById(id)

    override suspend fun discardItems(ids: List<Int>) {
        itemDao.discardItemsByIds(ids)
    }

}