/*
 * Copyright (c) 2025 Ashwin Rao (github:ashwinravrao).
 *
 * Licensed under the GNU General Public License v3.0 or later (GPLv3+).
 * See the LICENSE file or <https://www.gnu.org/licenses/> for details.
 */

package com.ashwinrao.packup.feature.main.ui.viewmodel

import com.ashwinrao.packup.domain.model.Item
import kotlinx.coroutines.flow.StateFlow

interface MainScreenViewModel {
    val items: StateFlow<List<Item>>
    val searchQuery: StateFlow<String>
    val filteredItems: StateFlow<List<Item>>
    val selectedItem: StateFlow<ItemSelection>
    fun updateSearchQuery(query: String)
    fun collapseSearchBar()
    fun selectItem(item: Item)
    fun unselectItem()
}

sealed interface ItemSelection {
    data object None : ItemSelection
    data class Selected(val item: Item) : ItemSelection
}
