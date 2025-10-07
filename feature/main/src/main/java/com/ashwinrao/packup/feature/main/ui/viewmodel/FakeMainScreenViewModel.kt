/*
 * Copyright (c) 2025 Ashwin Rao (github:ashwinravrao).
 *
 * Licensed under the GNU General Public License v3.0 or later (GPLv3+).
 * See the LICENSE file or <https://www.gnu.org/licenses/> for details.
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
