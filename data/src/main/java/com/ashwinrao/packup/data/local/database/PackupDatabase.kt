/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ashwinrao.packup.data.local.model.ItemEntity
import com.ashwinrao.packup.data.local.repository.ItemDao
import com.ashwinrao.packup.data.local.typeconverter.ItemTypeConverter
import com.ashwinrao.packup.data.local.typeconverter.LocationTypeConverter

@Database(
    entities = [ItemEntity::class],
    version = DatabaseConstants.DATABASE_VERSION,
    exportSchema = false,
)
@TypeConverters(LocationTypeConverter::class, ItemTypeConverter::class)
abstract class PackupDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}
