/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
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
