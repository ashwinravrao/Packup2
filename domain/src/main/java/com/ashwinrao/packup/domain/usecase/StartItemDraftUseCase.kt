/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.domain.usecase

import android.net.Uri
import com.ashwinrao.packup.domain.model.Item
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class StartItemDraftUseCase @Inject constructor() {
    operator fun invoke(uri: Uri?): Item {
        val uriString = uri?.let { Uri.encode(uri.toString()) }
        return Item(
            name = null,
            description = null,
            imageUri = uriString,
        )
    }
}
