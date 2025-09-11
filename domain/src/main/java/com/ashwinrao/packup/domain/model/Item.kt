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
) {
    companion object {
        fun roughDraft(photoUri: String?) = Item(
            name = null,
            photoUri = photoUri,
            description = null,
            locationType = null,
            itemType = ItemType.Draft,
        )

        val generated: List<Item>
            get() =
                listOf(
                    Item(
                        id = 0L,
                        name = "Spoon",
                        photoUri = "",
                        description = "An eating utensil that is concave.",
                        locationType = ItemLocationType.Room(name = "Kitchen"),
                    ),
                    Item(
                        id = 1L,
                        name = "Fork",
                        photoUri = "",
                        description = "An eating utensil that is pointy.",
                        locationType = ItemLocationType.Room(name = "Kitchen"),
                    ),
                    Item(
                        id = 2L,
                        name = "Knife",
                        photoUri = "",
                        description = "An eating utensil that is sharp and serrated.",
                        locationType = ItemLocationType.Room(name = "Kitchen"),
                    ),
                )
    }
}
