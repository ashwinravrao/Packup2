/*
 * Copyright (c) 2025 Ashwin Rao (github:ashwinravrao).
 *
 * Licensed under the GNU General Public License v3.0 or later (GPLv3+).
 * See the LICENSE file or <https://www.gnu.org/licenses/> for details.
 */

package com.ashwinrao.packup.domain.model

import android.net.Uri

data class Item(
    val id: Long? = null,
    val name: String? = null,
    val imageUri: Uri? = null,
    val description: String? = null,
    val state: CompositionState = CompositionState.Draft,
    val quantity: Int = 1,
    val tags: List<String> = emptyList(),
    val measurements: List<Measurement> = emptyList(),
)
