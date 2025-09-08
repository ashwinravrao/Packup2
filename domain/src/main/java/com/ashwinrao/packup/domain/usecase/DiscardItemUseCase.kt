/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.domain.usecase

import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.domain.repository.ItemRepository
import javax.inject.Inject

class DiscardItemUseCase
@Inject
constructor(private val repo: ItemRepository) {
    suspend operator fun invoke(item: Item) = repo.discardItem(item.id)
}
