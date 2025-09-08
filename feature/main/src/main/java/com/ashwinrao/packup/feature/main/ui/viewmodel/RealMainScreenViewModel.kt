/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.feature.main.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.domain.usecase.GetItemUseCase
import com.ashwinrao.packup.domain.usecase.GetItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealMainScreenViewModel
@Inject
constructor(
    private val getItemsUseCase: GetItemsUseCase,
    private val getItemUseCase: GetItemUseCase,
) : ViewModel(),
    MainScreenViewModel {
    private var _items = MutableStateFlow<List<Item>>(emptyList())
    override val items = _items.asStateFlow()

    // todo: move to a dedicated vm?
    private var _selectedItem = MutableStateFlow<Item?>(null)
    override val selectedItem: StateFlow<Item?> = _selectedItem.asStateFlow()

    init {
        fetchItems()
    }

    override fun fetchItems() {
        viewModelScope.launch {
            _items.value = getItemsUseCase()
        }
    }

    override fun fetchItem(id: Long) {
        viewModelScope.launch {
            _selectedItem.emit(getItemUseCase(id))
        }
    }
}
