/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake.viewmodel

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashwinrao.packup.domain.model.IntakeError
import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.domain.model.ValidatedFieldInput
import com.ashwinrao.packup.domain.usecase.CreateDraftItemUseCase
import com.ashwinrao.packup.domain.usecase.DiscardItemUseCase
import com.ashwinrao.packup.domain.usecase.GetItemUseCase
import com.ashwinrao.packup.domain.usecase.SaveItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealIntakeScreenViewModel
@Inject
constructor(
    private val ucSaveItem: SaveItemUseCase,
    private val ucDiscardItem: DiscardItemUseCase,
    private val ucGetItem: GetItemUseCase,
    private val ucCreateDraftItem: CreateDraftItemUseCase,
) : ViewModel(),
    IntakeScreenViewModel {

    private var hasNameBeenSet: Boolean = false
    private var hasDescriptionBeenSet: Boolean = false
    private var _currentItem = MutableStateFlow<Item?>(null)
    override val currentItem = _currentItem.asStateFlow()

    private var _nameField = MutableStateFlow(TextFieldValue())
    override val nameField = _nameField.asStateFlow()

    override val nameValidation: StateFlow<ValidatedFieldInput?> =
        _nameField.map {
            val name = it.text
            if (name.isBlank() && hasNameBeenSet) {
                ValidatedFieldInput(name, IntakeError.RequiredField)
            } else {
                null
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = null,
        )

    private var _descriptionField = MutableStateFlow(TextFieldValue())
    override val descriptionField = _descriptionField.asStateFlow()

    override val descriptionValidation: StateFlow<ValidatedFieldInput?> =
        _descriptionField.map {
            val description = it.text
            if (description.isBlank() && hasDescriptionBeenSet) {
                ValidatedFieldInput(description, IntakeError.RequiredField)
            } else {
                null
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = null,
        )

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

    override fun newItemAsDraft(uri: Uri?) {
        _currentItem.value = ucCreateDraftItem(uri)
    }

    override fun updateName(new: TextFieldValue) {
        _nameField.value = new
        hasNameBeenSet = true
    }

    override fun updateDescription(new: TextFieldValue) {
        _descriptionField.value = new
        hasDescriptionBeenSet = true
    }
}
