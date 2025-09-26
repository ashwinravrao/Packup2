/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
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
