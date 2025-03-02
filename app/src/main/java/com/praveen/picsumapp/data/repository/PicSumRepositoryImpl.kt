package com.praveen.picsumapp.data.repository

import com.praveen.picsumapp.data.remote.PicSumApiService
import com.praveen.picsumapp.domain.model.PicsumImage
import com.praveen.picsumapp.domain.repository.PicSumRepository
import com.praveen.picsumapp.domain.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


/**
 * Created by Praveen.Sharma on 01/03/25 - 10:45..
 *
 ***/
class PicSumRepositoryImpl @Inject constructor(
    private val apiService: PicSumApiService
) : PicSumRepository {
    override suspend fun getPicSumImages(): Flow<ResultState<List<PicsumImage>>> = flow {
        try {
            emit(ResultState.Loading) // Emit loading state
            val response = apiService.getPicSumImages().map { it.toDomainModel() }
            emit(ResultState.Success(response)) // Emit success state with data
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "An unexpected error occurred")) // Emit error state
        }
    }.flowOn(Dispatchers.IO) // Perform network request on IO thread

}