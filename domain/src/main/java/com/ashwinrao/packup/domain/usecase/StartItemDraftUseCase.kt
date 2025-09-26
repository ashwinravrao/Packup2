/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
 */

package com.ashwinrao.packup.domain.usecase

import android.net.Uri
import com.ashwinrao.packup.domain.model.CompositionState
import com.ashwinrao.packup.domain.model.Item
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class StartItemDraftUseCase @Inject constructor() {
    operator fun invoke(uri: Uri?) =
        Item(
            imageUri = uri,
            state = CompositionState.Draft,
        )
}
