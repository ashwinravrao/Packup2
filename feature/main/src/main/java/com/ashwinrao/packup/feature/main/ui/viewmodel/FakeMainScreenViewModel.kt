/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.feature.main.ui.viewmodel

import com.ashwinrao.packup.domain.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeMainScreenViewModel : MainScreenViewModel {
    override val items: StateFlow<List<Item>> = MutableStateFlow(emptyList())
}
