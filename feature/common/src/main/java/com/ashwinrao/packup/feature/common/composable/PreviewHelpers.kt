/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
 */

package com.ashwinrao.packup.feature.common.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode

/**
 * Checks whether the calling composable is "inspectable", aka a `@Preview`. If it is, return the
 * fake viewmodel implementation of T. Otherwise invoke a function so that the real implementation
 * can be instantiated via hilt. This is a workaround to a known issue - if a preview calls a composable
 * containing a viewmodel, the preview will not render.
 */
@Composable
fun <T> getViewModelForInspectionMode(fake: T, real: @Composable () -> T): T =
    if (LocalInspectionMode.current) fake else real()
