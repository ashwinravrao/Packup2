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
import com.ashwinrao.packup.intake.DirtyDesignator
import com.ashwinrao.packup.intake.model.IntakeField
import com.ashwinrao.packup.intake.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.map
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
    private val designator: DirtyDesignator,
) : ViewModel(),
    IntakeScreenViewModel {
    private var _currentItem = MutableStateFlow<Item?>(null)
    override val currentItem = _currentItem.asStateFlow()

    private val isNameFieldDirty =
        designator.isNameFieldDirty.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    private val isDescriptionFieldDirty =
        designator.isDescriptionFieldDirty.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    private val areRequiredFieldsDirty =
        designator.areRequiredFieldsDirty.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    private var _selectedName = MutableStateFlow(TextFieldValue())
    override val selectedName = _selectedName.asStateFlow()

    override val validatedName: StateFlow<ValidatedFieldInput> =
        combine(
            _selectedName,
            isNameFieldDirty
        ) { name, dirty ->
            val input = name.text
            ValidatedFieldInput(
                input = input,
                error =
                    if (dirty) ucValidateField(input = input, field = IntakeField.Name.toDomain())
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
        combine(
            _selectedDescription,
            isDescriptionFieldDirty
        ) { description, dirty ->
            val input = description.text
            ValidatedFieldInput(
                input = input,
                error =
                    if (dirty) ucValidateField(input = input, field = IntakeField.Description.toDomain())
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
            validatedDescription,
            areRequiredFieldsDirty,
        ) { name, description, requiredDirty ->
            requiredDirty && name.isValid && description.isValid
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    init {
        persistValidChangesToItem()
    }

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
        designator.designateDirty(IntakeField.Name)
        _selectedName.value = new
    }

    override fun updateDescription(new: TextFieldValue) {
        designator.designateDirty(IntakeField.Description)
        _selectedDescription.value = new
    }

    private fun persistValidChangesToItem() {
        val nameUpdates = validatedName
            .filter { it.isValid }
            .map { IntakeField.Name to it.input }
            .distinctUntilChanged()

        val descriptionUpdates = validatedDescription
            .filter { it.isValid }
            .map { IntakeField.Description to it.input }
            .distinctUntilChanged()

        merge(nameUpdates, descriptionUpdates)
            .onEach { (field, value) ->
                _currentItem.value?.let { current ->
                    _currentItem.value = when (field) {
                        IntakeField.Name -> current.copy(name = value)
                        IntakeField.Description -> current.copy(description = value)
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}
