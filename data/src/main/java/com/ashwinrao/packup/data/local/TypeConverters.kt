// Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.

package com.ashwinrao.packup.data.local

import androidx.room.TypeConverter
import com.ashwinrao.packup.domain.model.ItemLocationType
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson

class LocationTypeConverter {
    private val moshi =
        Moshi.Builder()
            .add(LocationTypeAdapter())
            .build()

    private val adapter = moshi.adapter(ItemLocationType::class.java)

    @TypeConverter
    fun fromLocationTypeToJson(locationType: ItemLocationType?): String? = locationType?.let { adapter.toJson(it) }

    @TypeConverter
    fun fromJsonToLocationType(json: String?): ItemLocationType? = json?.let { adapter.fromJson(it) }
}

class LocationTypeAdapter : JsonAdapter<ItemLocationType>() {
    @FromJson
    override fun fromJson(reader: JsonReader): ItemLocationType? {
        reader.beginObject()
        var type: String? = null
        var name: String? = null
        var latitude: Double? = null
        var longitude: Double? = null

        while (reader.hasNext()) {
            when (reader.nextName()) {
                "type" -> type = reader.nextString()
                "name" -> name = reader.nextString()
                "latitude" -> latitude = reader.nextDouble()
                "longitude" -> longitude = reader.nextDouble()
                else -> reader.skipValue()
            }
        }
        reader.endObject()

        return when (type) {
            "room" -> ItemLocationType.Room(name = name ?: "")
            "coordinates" ->
                ItemLocationType.Coordinates(
                    latitude = latitude,
                    longitude = longitude,
                )

            else -> throw JsonDataException("Unknown location type: $type")
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: ItemLocationType?) {
        if (value == null) {
            writer.nullValue()
            return
        }

        writer.beginObject()
        when (value) {
            is ItemLocationType.Room -> {
                writer.name("type").value("room")
                writer.name("name").value(value.name)
            }

            is ItemLocationType.Coordinates -> {
                writer.name("type").value("coordinates")
                writer.name("latitude").value(value.latitude)
                writer.name("longitude").value(value.longitude)
            }
        }
        writer.endObject()
    }
}
