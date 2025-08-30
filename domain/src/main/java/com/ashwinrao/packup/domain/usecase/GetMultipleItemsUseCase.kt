package com.ashwinrao.packup.domain.usecase

import com.ashwinrao.packup.domain.repository.ItemRepository
import javax.inject.Inject

class GetMultipleItemsUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke() =
        repository.getItems()
}
