package com.ashwinrao.packup.domain.usecase

import com.ashwinrao.packup.domain.repository.ItemRepository
import javax.inject.Inject

class GetItemUseCase @Inject constructor(
    private val repo: ItemRepository
) {
    suspend operator fun invoke(id: Int) =
        repo.getItem(id)
}
