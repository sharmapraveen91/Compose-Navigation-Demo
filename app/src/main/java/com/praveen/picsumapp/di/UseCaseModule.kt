package com.praveen.picsumapp.di

import com.praveen.picsumapp.domain.repository.PicSumRepository
import com.praveen.picsumapp.domain.usecase.GetPicSumImagesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetPicSumImagesUseCase(repository: PicSumRepository): GetPicSumImagesUseCase {
        return GetPicSumImagesUseCase(repository)
    }
}