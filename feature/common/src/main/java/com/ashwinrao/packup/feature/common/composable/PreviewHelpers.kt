/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
 */

package com.ashwinrao.packup.feature.common.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode

/**
 * If the calling composable is "inspectable" (ie. a composable preview), return the fake viewmodel.
 * Otherwise, return the real thing. This is a workaround for a known issue preventing previews from
 * rendering for composables where a viewmodel is instantiated.
 */
@Composable
fun <T> getViewModelForInspectionMode(fake: T, real: @Composable () -> T): T =
    if (LocalInspectionMode.current) fake else real()
