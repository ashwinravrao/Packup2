/*
 * Copyright (c) 2025 Ashwin Rao (github:ashwinravrao).
 *
 * Licensed under the GNU General Public License v3.0 or later (GPLv3+).
 * See the LICENSE file or <https://www.gnu.org/licenses/> for details.
 */

package com.ashwinrao.packup.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ashwinrao.packup.data.local.model.ItemEntity
import com.ashwinrao.packup.data.local.repository.ItemDao
import com.ashwinrao.packup.data.local.typeconverter.CompositionStateConverter
import com.ashwinrao.packup.data.local.typeconverter.StringListConverter
import com.ashwinrao.packup.data.local.typeconverter.MeasurementConverter
import com.ashwinrao.packup.data.local.typeconverter.UriConverter

@Database(
    entities = [ItemEntity::class],
    version = DatabaseConstants.DATABASE_VERSION,
    exportSchema = false,
)
@TypeConverters(
    CompositionStateConverter::class,
    MeasurementConverter::class,
    StringListConverter::class,
    UriConverter::class,
)
abstract class PackupDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}
