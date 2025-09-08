/*
 *  Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake.viewmodel

import com.ashwinrao.packup.domain.model.Item
import kotlinx.coroutines.flow.StateFlow

interface IntakeScreenViewModel {
    val currentItem: StateFlow<Item?>

    fun fetchCurrentItem(id: Int)

    fun discardCurrentItem()

    fun saveCurrentItem()
}
