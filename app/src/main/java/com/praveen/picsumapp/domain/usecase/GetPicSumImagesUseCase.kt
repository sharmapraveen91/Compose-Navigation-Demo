package com.praveen.picsumapp.domain.usecase

import com.praveen.picsumapp.domain.model.PicsumImage
import com.praveen.picsumapp.domain.repository.PicSumRepository
import com.praveen.picsumapp.domain.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetPicSumImagesUseCase(private val repository: PicSumRepository) {
    operator fun invoke(): Flow<ResultState<List<PicsumImage>>> = flow {
        emit(ResultState.Loading) // Emit loading state before fetching data
        repository.getPicSumImages().collect { result ->
            emit(result) // Directly emit the result coming from repository
        }
    }.catch { e ->
        emit(ResultState.Error(e.localizedMessage ?: "Unknown error")) // Emit error state on exception
    }.flowOn(Dispatchers.IO) // Ensure operation runs on IO thread
}