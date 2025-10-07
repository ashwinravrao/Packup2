/*
 * Copyright (c) 2025 Ashwin Rao (github:ashwinravrao).
 *
 * Licensed under the GNU General Public License v3.0 or later (GPLv3+).
 * See the LICENSE file or <https://www.gnu.org/licenses/> for details.
 */

package com.ashwinrao.packup.intake.model

enum class IntakeField {
    Name,
    Description,
    ;

    companion object {
        val REQUIRED: Set<IntakeField> = setOf(Name, Description)
    }
}
