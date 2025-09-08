/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.domain.model

sealed interface ItemType {
    data object Draft : ItemType
    data object Complete : ItemType
    data object Corrupted : ItemType
}
