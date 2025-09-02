package com.ashwinrao.packup.feature.main.ui.viewmodel

import com.ashwinrao.packup.domain.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeMainScreenViewModel : MainScreenViewModel {

    override val items: StateFlow<List<Item>> = MutableStateFlow(emptyList())

    override val selectedItem: StateFlow<Item?> = MutableStateFlow(null)

    override fun fetchItems() {}

    override fun fetchItem(id: Int) {}

    override fun packItem(item: Item) {}

}