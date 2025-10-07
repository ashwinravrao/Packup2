/*
 * Copyright (c) 2025 Ashwin Rao (github:ashwinravrao).
 *
 * Licensed under the GNU General Public License v3.0 or later (GPLv3+).
 * See the LICENSE file or <https://www.gnu.org/licenses/> for details.
 */

package com.ashwinrao.packup.data.local.typeconverter

import android.net.Uri
import androidx.room.TypeConverter
import androidx.core.net.toUri

object UriConverter {

    @TypeConverter
    @JvmStatic
    fun fromUriToString(uri: Uri?): String? = uri?.toString()

    @TypeConverter
    @JvmStatic
    fun fromStringToUri(uriString: String?): Uri? = uriString?.toUri()
}
