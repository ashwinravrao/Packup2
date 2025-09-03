package com.ashwinrao.packup.feature.main.ui.viewmodel

import com.ashwinrao.packup.domain.model.Item
import kotlinx.coroutines.flow.StateFlow

interface MainScreenViewModel {
    val items: StateFlow<List<Item>>
    val selectedItem: StateFlow<Item?>
    fun fetchItems()
    fun fetchItem(id: Int)
}