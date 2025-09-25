/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
 */

package com.ashwinrao.packup.feature.main.ui.viewmodel

import com.ashwinrao.packup.domain.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeMainScreenViewModel : MainScreenViewModel {
    override val items: StateFlow<List<Item>> = MutableStateFlow(emptyList())
    override val selectedItem: StateFlow<ItemSelection> = MutableStateFlow(ItemSelection.None)

    override fun selectItem(item: Item) {}

    override fun unselectItem() {}
}
