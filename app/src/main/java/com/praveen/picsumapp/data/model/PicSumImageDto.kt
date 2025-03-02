package com.praveen.picsumapp.data.model

import com.praveen.picsumapp.domain.model.PicsumImage
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true) // Required for Moshi to generate adapter
data class PicSumImageDto(
    @Json(name = "id") val id: String,
    @Json(name = "author") val author: String,
    @Json(name = "width") val width: Int,
    @Json(name = "height") val height: Int,
    @Json(name = "url") val url: String,
    @Json(name = "download_url") val downloadUrl: String
){
    fun toDomainModel(): PicsumImage {
        return PicsumImage(
            id = id,
            download_url = downloadUrl,
            author = author,
        )
    }
}
