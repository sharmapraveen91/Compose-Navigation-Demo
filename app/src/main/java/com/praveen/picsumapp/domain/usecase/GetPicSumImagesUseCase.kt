package com.praveen.picsumapp.domain.usecase

import com.praveen.picsumapp.domain.model.PicsumImage
import com.praveen.picsumapp.domain.repository.PicSumRepository
import com.praveen.picsumapp.domain.utils.ResultState
import kotlinx.coroutines.flow.Flow

class GetPicSumImagesUseCase(private val repository: PicSumRepository) {
    suspend operator fun invoke(): Flow<ResultState<List<PicsumImage>>> = repository.getPicSumImages()
}