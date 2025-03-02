package com.praveen.picsumapp.domain.repository

import com.praveen.picsumapp.domain.model.PicsumImage
import com.praveen.picsumapp.domain.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface PicSumRepository {
    suspend fun getPicSumImages(): Flow<ResultState<List<PicsumImage>>>
}