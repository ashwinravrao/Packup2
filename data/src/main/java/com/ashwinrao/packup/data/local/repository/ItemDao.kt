/*
 * Copyright (c) 2025 Ashwin Rao (github:ashwinravrao).
 *
 * Licensed under the GNU General Public License v3.0 or later (GPLv3+).
 * See the LICENSE file or <https://www.gnu.org/licenses/> for details.
 */

package com.ashwinrao.packup.data.local.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.ashwinrao.packup.data.local.model.ItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Dao
@Singleton
interface ItemDao {
    @Query("SELECT * FROM items")
    fun getItems(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getItem(id: Long): ItemEntity

    @Insert(onConflict = REPLACE)
    suspend fun saveItem(item: ItemEntity): Long

    @Query("DELETE FROM items WHERE id = :id")
    suspend fun discardItemById(id: Long)

    @Query("DELETE FROM items WHERE id IN (:ids)")
    suspend fun discardItemsByIds(ids: List<Long>)
}
