/* Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved. */

package com.ashwinrao.packup.data.local.repository

import com.ashwinrao.packup.domain.repository.ItemRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindItemRepository(
        impl: LocalItemRepository
    ): ItemRepository
}