package com.ashwinrao.packup.data.local.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ashwinrao.packup.data.local.model.ItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Dao
@Singleton
interface ItemDao {

    @Query("SELECT * FROM items")
    suspend fun getItems(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getItem(id: Int): ItemEntity

    @Insert
    suspend fun packItem(item: ItemEntity)

    @Query("DELETE FROM items WHERE id = :id")
    suspend fun discardItemById(id: Int)

    @Query("DELETE FROM items WHERE id IN (:ids)")
    suspend fun discardItemsByIds(ids: List<Int>)

}
