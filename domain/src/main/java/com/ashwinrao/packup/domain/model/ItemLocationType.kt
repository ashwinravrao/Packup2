// Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.

package com.ashwinrao.packup.domain.model

sealed interface ItemLocationType {
    data class Room(val name: String) : ItemLocationType

    data class Coordinates(val latitude: Double?, val longitude: Double?) : ItemLocationType
}
