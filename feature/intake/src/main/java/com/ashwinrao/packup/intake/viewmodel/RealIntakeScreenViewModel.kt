/*
 *  Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.domain.usecase.DiscardItemUseCase
import com.ashwinrao.packup.domain.usecase.SaveItemUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RealIntakeScreenViewModel @Inject constructor(
    private val saveItemUC: SaveItemUseCase,
    private val discardItemUC: DiscardItemUseCase,
) : IntakeScreenViewModel, ViewModel() {

    private var _currentItem = MutableStateFlow<Item?>(null)
    override val currentItem = _currentItem.asStateFlow()

    override fun discardCurrentItem() {
        _currentItem.value?.let { item ->
            viewModelScope.launch {
                discardItemUC(item)
            }
        }
    }

    override fun saveCurrentItem() {
        _currentItem.value?.let { item ->
            viewModelScope.launch {
                saveItemUC(item)
            }
        }
    }
}