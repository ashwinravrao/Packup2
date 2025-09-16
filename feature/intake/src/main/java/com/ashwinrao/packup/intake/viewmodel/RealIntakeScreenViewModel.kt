/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.intake.viewmodel

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.domain.model.ValidatedFieldInput
import com.ashwinrao.packup.domain.usecase.CreateDraftItemUseCase
import com.ashwinrao.packup.domain.usecase.DiscardItemUseCase
import com.ashwinrao.packup.domain.usecase.GetItemUseCase
import com.ashwinrao.packup.domain.usecase.SaveItemUseCase
import com.ashwinrao.packup.domain.usecase.ValidateFieldUseCase
import com.ashwinrao.packup.intake.FieldInspector
import com.ashwinrao.packup.intake.model.IntakeField
import com.ashwinrao.packup.intake.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RealIntakeScreenViewModel
@Inject
constructor(
    private val ucSaveItem: SaveItemUseCase,
    private val ucDiscardItem: DiscardItemUseCase,
    private val ucGetItem: GetItemUseCase,
    private val ucCreateDraftItem: CreateDraftItemUseCase,
    private val ucValidateField: ValidateFieldUseCase,
    private val inspector: FieldInspector,
) : ViewModel(),
    IntakeScreenViewModel {
    private var _currentItem = MutableStateFlow<Item?>(null)
    override val currentItem = _currentItem.asStateFlow()

    private var _selectedName = MutableStateFlow(TextFieldValue())
    override val selectedName = _selectedName.asStateFlow()

    override val validatedName: StateFlow<ValidatedFieldInput> =
        _selectedName.mapLatest {
            val name = it.text
            val dirty = inspector.isDirty(IntakeField.Name)
            ValidatedFieldInput(
                input = name,
                error =
                    if (dirty) ucValidateField(input = name, field = IntakeField.Name.toDomain())
                    else null,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ValidatedFieldInput(),
        )

    private var _selectedDescription = MutableStateFlow(TextFieldValue())
    override val selectedDescription = _selectedDescription.asStateFlow()

    override val validatedDescription: StateFlow<ValidatedFieldInput> =
        _selectedDescription.mapLatest {
            val description = it.text
            val dirty = inspector.isDirty(IntakeField.Description)
            ValidatedFieldInput(
                input = description,
                error =
                    if (dirty) ucValidateField(input = description, field = IntakeField.Description.toDomain())
                    else null,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ValidatedFieldInput(),
        )

    override val isFormValid: StateFlow<Boolean> =
        combine(
            validatedName,
            validatedDescription
        ) { name, description ->
            inspector.areRequiredFieldsDirty
                && name.isValid
                && description.isValid
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = false
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
        inspector.markAsDirty(IntakeField.Name)
        _selectedName.value = new
    }

    override fun updateDescription(new: TextFieldValue) {
        inspector.markAsDirty(IntakeField.Description)
        _selectedDescription.value = new
    }
}
