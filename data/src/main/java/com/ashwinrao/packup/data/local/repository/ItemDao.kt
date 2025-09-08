/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.data.local.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.ashwinrao.packup.data.local.model.ItemEntity
import javax.inject.Singleton

@Dao
@Singleton
interface ItemDao {
    @Query("SELECT * FROM items")
    suspend fun getItems(): List<ItemEntity>

    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getItem(id: Long): ItemEntity

    @Insert(onConflict = REPLACE)
    suspend fun saveItem(item: ItemEntity): Long

    @Query("DELETE FROM items WHERE id = :id")
    suspend fun discardItemById(id: Long)

    @Query("DELETE FROM items WHERE id IN (:ids)")
    suspend fun discardItemsByIds(ids: List<Long>)
}
