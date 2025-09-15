/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake.viewmodel

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashwinrao.packup.domain.model.FieldError
import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.domain.model.ValidatedFieldInput
import com.ashwinrao.packup.domain.usecase.CreateDraftItemUseCase
import com.ashwinrao.packup.domain.usecase.DiscardItemUseCase
import com.ashwinrao.packup.domain.usecase.GetItemUseCase
import com.ashwinrao.packup.domain.usecase.SaveItemUseCase
import com.ashwinrao.packup.intake.FieldHygienist
import com.ashwinrao.packup.intake.model.IntakeField
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
    private val hygienist: FieldHygienist,
) : ViewModel(),
    IntakeScreenViewModel {
    private var _currentItem = MutableStateFlow<Item?>(null)
    override val currentItem = _currentItem.asStateFlow()

    private var _selectedName = MutableStateFlow(TextFieldValue())
    override val selectedName = _selectedName.asStateFlow()

    override val validatedName: StateFlow<ValidatedFieldInput> =
        _selectedName.map {
            ValidatedFieldInput(
                input = it.text,
                error =
                    if (it.text.isBlank() && hygienist.isDirty(IntakeField.Name)) {
                        FieldError.RequiredButAbsent
                    } else {
                        null
                    },
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ValidatedFieldInput(),
        )

    private var _selectedDescription = MutableStateFlow(TextFieldValue())
    override val selectedDescription = _selectedDescription.asStateFlow()

    override val validatedDescription: StateFlow<ValidatedFieldInput> =
        _selectedDescription.map {
            val description = it.text
            ValidatedFieldInput(
                input = description,
                error =
                    if (description.isBlank() && hygienist.isDirty(IntakeField.Description)) {
                        FieldError.RequiredButAbsent
                    } else {
                        null
                    },
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ValidatedFieldInput(),
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
        _selectedName.value = new
        hygienist.markDirty(IntakeField.Name)
    }

    override fun updateDescription(new: TextFieldValue) {
        _selectedDescription.value = new
        hygienist.markDirty(IntakeField.Description)
    }
}
