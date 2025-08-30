package com.ashwinrao.packup.domain.model

sealed interface ItemLocationType {
    data class Room(
        val name: String,
    ) : ItemLocationType
    data class Coordinates(
        val latitude: Double?,
        val longitude: Double?,
    ) : ItemLocationType
}

