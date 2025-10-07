/*
 * Copyright (c) 2025 Ashwin Rao (github:ashwinravrao).
 *
 * Licensed under the GNU General Public License v3.0 or later (GPLv3+).
 * See the LICENSE file or <https://www.gnu.org/licenses/> for details.
 */

package com.ashwinrao.packup.domain.model

sealed interface FieldError {
    data object RequiredButAbsent : FieldError

    companion object {
        val allErrors: List<FieldError>
            get() = listOf(RequiredButAbsent)
    }
}
