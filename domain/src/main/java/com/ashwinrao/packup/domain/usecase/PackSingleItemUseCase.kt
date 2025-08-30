package com.ashwinrao.packup.domain.usecase

import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.domain.repository.ItemRepository
import javax.inject.Inject

class PackSingleItemUseCase @Inject constructor(
    private val repo: ItemRepository
) {
    suspend operator fun invoke(item: Item) =
        repo.packItem(item)
}
