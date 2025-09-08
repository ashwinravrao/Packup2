/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.data.local.database

import android.content.Context
import androidx.room.Room
import com.ashwinrao.packup.data.local.repository.ItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providePackupDatabase(@ApplicationContext context: Context): PackupDatabase = Room.databaseBuilder(
        context,
        PackupDatabase::class.java,
        DatabaseConstants.DATABASE_NAME,
    ).build()

    @Provides
    fun provideItemDao(database: PackupDatabase): ItemDao = database.itemDao()
}
