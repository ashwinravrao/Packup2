/*
 * Copyright (c) 2025 Ashwin Rao (github:ashwinravrao).
 *
 * Licensed under the GNU General Public License v3.0 or later (GPLv3+).
 * See the LICENSE file or <https://www.gnu.org/licenses/> for details.
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
import kotlinx.coroutines.flow.combine
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

    private val _searchQuery = MutableStateFlow("")
    override val searchQuery = _searchQuery.asStateFlow()

    override val filteredItems = combine(items, searchQuery) { allItems, query ->
        if (query.isBlank()) {
            allItems
        } else {
            allItems.filter { item ->
                val nameMatch = item.name?.contains(query, ignoreCase = true) ?: false
                val descMatch = item.description?.contains(query, ignoreCase = true) ?: false
                nameMatch || descMatch
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _selectedItem = MutableStateFlow<ItemSelection>(ItemSelection.None)
    override val selectedItem = _selectedItem.asStateFlow()

    override fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    override fun collapseSearchBar() {
        _searchQuery.value = ""
    }

    override fun selectItem(item: Item) {
        _selectedItem.value = ItemSelection.Selected(item)
    }

    override fun unselectItem() {
        _selectedItem.value = ItemSelection.None
    }
}
