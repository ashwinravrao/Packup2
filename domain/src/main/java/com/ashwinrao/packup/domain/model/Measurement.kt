/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
 */

package com.ashwinrao.packup.domain.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface Measurement {
    val value: Double
    val name: String
    val abbreviated: String

    @Serializable
    sealed interface Weight : Measurement {
        @Serializable
        data class KG(override val value: Double) : Weight {
            override val name: String
                get() {
                    val suffix = if (value > 1.0) "s" else ""
                    return "kilogram$suffix"
                }
            override val abbreviated: String
                get() = "kg"
        }

        @Serializable
        data class OZ(override val value: Double) : Weight {
            override val name: String
                get() {
                    val suffix = if (value > 1.0) "s" else ""
                    return "ounce$suffix"
                }
            override val abbreviated: String
                get() = "oz"
        }

        @Serializable
        data class LB(override val value: Double) : Weight {
            override val name: String
                get() {
                    val suffix = if (value > 1.0) "s" else ""
                    return "pound$suffix"
                }
            override val abbreviated: String
                get() = "oz"
        }
    }

    @Serializable
    sealed interface LWH : Measurement {
        @Serializable
        data class IN(override val value: Double) : LWH {
            override val name: String
                get() {
                    val suffix = if (value > 1.0) "es" else ""
                    return "inch$suffix"
                }
            override val abbreviated: String
                get() = "in"
        }

        @Serializable
        data class FT(override val value: Double) : LWH {
            override val name: String
                get() = if (value > 1.0) "feet" else "foot"
            override val abbreviated: String
                get() = "in"
        }

        @Serializable
        data class CM(override val value: Double) : LWH {
            override val name: String
                get() {
                    val suffix = if (value > 1.0) "s" else ""
                    return "centimeter$suffix"
                }
            override val abbreviated: String
                get() = "cm"
        }

        @Serializable
        data class MM(override val value: Double) : LWH {
            override val name: String
                get() {
                    val suffix = if (value > 1.0) "s" else ""
                    return "millimeter$suffix"
                }
            override val abbreviated: String
                get() = "mm"
        }

        @Serializable
        data class M(override val value: Double) : LWH {
            override val name: String
                get() {
                    val suffix = if (value > 1.0) "s" else ""
                    return "meter$suffix"
                }
            override val abbreviated: String
                get() = "m"
        }
    }
}
