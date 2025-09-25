/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
 */

package com.ashwinrao.packup.domain.model

data class Item(
    val id: Long? = null,
    val name: String? = null,
    val imageUri: String? = null,
    val description: String? = null,
    val state: CompositionState = CompositionState.Draft,
    val quantity: Int = 1,
    val tags: List<String> = emptyList(),
    val measurements: List<Measurement> = emptyList(),
)
