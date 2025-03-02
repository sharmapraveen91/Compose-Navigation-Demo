package com.praveen.picsumapp.data.remote

import com.praveen.picsumapp.data.model.PicSumImageDto
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Praveen.Sharma on 01/03/25 - 10:22..
 *
 ***/
interface PicSumApiService {
    @GET("v2/list")
    suspend fun getPicSumImages(@Query("limit") limit: Int = 50): List<PicSumImageDto>
}