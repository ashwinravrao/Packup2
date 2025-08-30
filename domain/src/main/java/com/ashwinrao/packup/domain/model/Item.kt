package com.ashwinrao.packup.domain.model

data class Item(
    val id: Int,
    val name: String?,
    val photoUri: String,
    val description: String?,
    val locationType: ItemLocationType,
)
