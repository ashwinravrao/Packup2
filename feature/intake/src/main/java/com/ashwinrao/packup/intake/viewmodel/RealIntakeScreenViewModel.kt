/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.domain.usecase.DiscardItemUseCase
import com.ashwinrao.packup.domain.usecase.GetItemUseCase
import com.ashwinrao.packup.domain.usecase.SaveItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealIntakeScreenViewModel
@Inject
constructor(
    private val ucSaveItem: SaveItemUseCase,
    private val ucDiscardItem: DiscardItemUseCase,
    private val ucGetItem: GetItemUseCase,
) : ViewModel(),
    IntakeScreenViewModel {
    private var _currentItem = MutableStateFlow<Item?>(null)
    override val currentItem = _currentItem.asStateFlow()

    override fun fetchCurrentItem(id: Long) {
        viewModelScope.launch {
            _currentItem.value = ucGetItem(id)
        }
    }

    override fun discardCurrentItem() {
        _currentItem.value?.let { item ->
            viewModelScope.launch {
                ucDiscardItem(item)
            }
        }
    }

    override fun saveCurrentItem() {
        _currentItem.value?.let { item ->
            viewModelScope.launch {
                ucSaveItem(item)
            }
        }
    }
}
