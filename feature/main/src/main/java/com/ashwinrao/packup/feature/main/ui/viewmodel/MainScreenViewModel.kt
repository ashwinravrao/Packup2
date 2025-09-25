/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
 */

package com.ashwinrao.packup.feature.main.ui.viewmodel

import com.ashwinrao.packup.domain.model.Item
import kotlinx.coroutines.flow.StateFlow

interface MainScreenViewModel {
    val items: StateFlow<List<Item>>
    val selectedItem: StateFlow<ItemSelection>
    fun selectItem(item: Item)
    fun unselectItem()
}

sealed interface ItemSelection {
    data object None : ItemSelection
    data class Selected(val item: Item) : ItemSelection
}
