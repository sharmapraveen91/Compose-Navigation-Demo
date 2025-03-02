package com.praveen.picsumapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Praveen.Sharma on 01/03/25 - 10:23..
 *
 ***/
@Parcelize
data class PicsumImage(
    val id: String,
    val author: String,
    val download_url: String
): Parcelable
