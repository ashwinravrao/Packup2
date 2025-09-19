/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.feature.main.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.domain.usecase.GetItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RealMainScreenViewModel
@Inject
constructor(getItemsUseCase: GetItemsUseCase) :
    ViewModel(),
    MainScreenViewModel {
    override val items = getItemsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList(),
    )

    private val _selectedItem = MutableStateFlow<ItemSelection>(ItemSelection.None)
    override val selectedItem = _selectedItem.asStateFlow()

    override fun selectItem(item: Item) {
        _selectedItem.value = ItemSelection.Selected(item)
    }

    override fun unselectItem() {
        _selectedItem.value = ItemSelection.None
    }
}
