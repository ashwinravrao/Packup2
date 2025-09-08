// Copyright (c) 2025 Ashwin R. Rao (github.com/ashwinravrao). All rights reserved.

package com.ashwinrao.packup.feature.camera.viewmodel

import com.ashwinrao.packup.domain.model.Item
import kotlinx.coroutines.flow.StateFlow

interface CameraViewModel {
    fun saveItemDraft(item: Item): StateFlow<Long>
}
