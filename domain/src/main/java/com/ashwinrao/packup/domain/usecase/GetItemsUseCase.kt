/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.domain.usecase

import com.ashwinrao.packup.domain.repository.ItemRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetItemsUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke() =
        repository.getItems()
}
