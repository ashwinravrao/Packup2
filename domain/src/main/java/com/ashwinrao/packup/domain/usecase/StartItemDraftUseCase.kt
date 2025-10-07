/*
 * Copyright (c) 2025 Ashwin Rao (github:ashwinravrao).
 *
 * Licensed under the GNU General Public License v3.0 or later (GPLv3+).
 * See the LICENSE file or <https://www.gnu.org/licenses/> for details.
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
