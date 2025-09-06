/* Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved. */

package com.ashwinrao.packup.domain.usecase

import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.domain.repository.ItemRepository
import javax.inject.Inject

class DiscardItemsUseCase @Inject constructor(
    private val repo: ItemRepository
) {
    suspend operator fun invoke(items: List<Item>) =
        repo.discardItems(items.map { it.id })
}
