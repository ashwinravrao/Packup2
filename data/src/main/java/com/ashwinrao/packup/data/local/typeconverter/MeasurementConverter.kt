/*
 * Copyright (c) 2025 Ashwin Rao (github:ashwinravrao).
 *
 * Licensed under the GNU General Public License v3.0 or later (GPLv3+).
 * See the LICENSE file or <https://www.gnu.org/licenses/> for details.
 */

package com.ashwinrao.packup.data.local.typeconverter

import androidx.room.TypeConverter
import com.ashwinrao.packup.domain.model.Measurement
import kotlinx.serialization.json.Json

object MeasurementConverter {

    private val json = Json {
        classDiscriminator = "type"
        ignoreUnknownKeys = true
    }

    @TypeConverter
    @JvmStatic
    fun fromListToString(value: List<Measurement>?): String? {
        return value?.let { json.encodeToString(it) }
    }

    @TypeConverter
    @JvmStatic
    fun fromStringToList(value: String?): List<Measurement>? {
        return value?.let { json.decodeFromString(it) }
    }
}
