/*
 * Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.
 */

package com.ashwinrao.packup.domain.model

sealed interface CompositionState {
    data object Draft : CompositionState
    data object Complete : CompositionState
    data object Corrupted : CompositionState
}
