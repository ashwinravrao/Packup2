/*
 * Copyright (c) 2025 Ashwin Rao (github:ashwinravrao).
 *
 * Licensed under the GNU General Public License v3.0 or later (GPLv3+).
 * See the LICENSE file or <https://www.gnu.org/licenses/> for details.
 */

package com.ashwinrao.packup.domain.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface Measurement {
    val value: Double
    val name: String
    val abbreviated: String
    val measureOf: MeasureOf

    @Serializable
    data class KG(override val value: Double) : Measurement {
        override val name: String
            get() {
                val suffix = if (value > 1.0) "s" else ""
                return "kilogram$suffix"
            }
        override val abbreviated: String
            get() = "kg"
        override val measureOf: MeasureOf
            get() = MeasureOf.Weight
    }

    @Serializable
    data class OZ(override val value: Double) : Measurement {
        override val name: String
            get() {
                val suffix = if (value > 1.0) "s" else ""
                return "ounce$suffix"
            }
        override val abbreviated: String
            get() = "oz"
        override val measureOf: MeasureOf
            get() = MeasureOf.Weight
    }

    @Serializable
    data class LB(override val value: Double) : Measurement {
        override val name: String
            get() {
                val suffix = if (value > 1.0) "s" else ""
                return "pound$suffix"
            }
        override val abbreviated: String
            get() = "lb"
        override val measureOf: MeasureOf
            get() = MeasureOf.Weight
    }

    @Serializable
    data class IN(override val value: Double) : Measurement {
        override val name: String
            get() {
                val suffix = if (value > 1.0) "es" else ""
                return "inch$suffix"
            }
        override val abbreviated: String
            get() = "in"
        override val measureOf: MeasureOf
            get() = MeasureOf.LxHxW
    }

    @Serializable
    data class FT(override val value: Double) : Measurement {
        override val name: String
            get() = if (value > 1.0) "feet" else "foot"
        override val abbreviated: String
            get() = "ft"
        override val measureOf: MeasureOf
            get() = MeasureOf.LxHxW
    }

    @Serializable
    data class CM(override val value: Double) : Measurement {
        override val name: String
            get() {
                val suffix = if (value > 1.0) "s" else ""
                return "centimeter$suffix"
            }
        override val abbreviated: String
            get() = "cm"
        override val measureOf: MeasureOf
            get() = MeasureOf.LxHxW
    }

    @Serializable
    data class MM(override val value: Double) : Measurement {
        override val name: String
            get() {
                val suffix = if (value > 1.0) "s" else ""
                return "millimeter$suffix"
            }
        override val abbreviated: String
            get() = "mm"
        override val measureOf: MeasureOf
            get() = MeasureOf.LxHxW
    }

    @Serializable
    data class M(override val value: Double) : Measurement {
        override val name: String
            get() {
                val suffix = if (value > 1.0) "s" else ""
                return "meter$suffix"
            }
        override val abbreviated: String
            get() = "m"
        override val measureOf: MeasureOf
            get() = MeasureOf.LxHxW
    }
}
