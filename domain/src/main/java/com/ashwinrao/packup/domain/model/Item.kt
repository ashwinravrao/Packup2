/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.domain.model

data class Item(
    val id: Long = 0L,
    val name: String?,
    val photoUri: String?,
    val description: String?,
    val locationType: ItemLocationType?,
    val itemType: ItemType? = ItemType.Draft,
)
