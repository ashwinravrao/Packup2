/* Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved. */

package com.ashwinrao.packup.domain.model

data class Item(
    val id: Int,
    val name: String?,
    val photoUri: String,
    val description: String?,
    val locationType: ItemLocationType,
) {
    companion object {
        val generated: List<Item>
            get() = listOf(
                Item(
                    id = 0,
                    name = "Spoon",
                    photoUri = "",
                    description = "An eating utensil that is concave.",
                    locationType = ItemLocationType.Room(name = "Kitchen")
                ),
                Item(
                    id = 1,
                    name = "Fork",
                    photoUri = "",
                    description = "An eating utensil that is pointy.",
                    locationType = ItemLocationType.Room(name = "Kitchen")
                ),
                Item(
                    id = 2,
                    name = "Knife",
                    photoUri = "",
                    description = "An eating utensil that is sharp and serrated.",
                    locationType = ItemLocationType.Room(name = "Kitchen")
                ),
            )
    }
}
