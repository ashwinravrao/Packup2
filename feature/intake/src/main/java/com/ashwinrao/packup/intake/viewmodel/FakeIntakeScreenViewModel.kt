/*
 *  Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake.viewmodel

import com.ashwinrao.packup.domain.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeIntakeScreenViewModel : IntakeScreenViewModel {
    override val currentItem: StateFlow<Item?> = MutableStateFlow(null)
    override fun fetchCurrentItem(id: Int) {}
    override fun discardCurrentItem() {}
    override fun saveCurrentItem() {}
}