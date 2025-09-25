/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
 */

package com.ashwinrao.packup.domain.model

sealed interface CompositionState {
    data object Draft : CompositionState
    data object Complete : CompositionState
    data object Corrupted : CompositionState
}
