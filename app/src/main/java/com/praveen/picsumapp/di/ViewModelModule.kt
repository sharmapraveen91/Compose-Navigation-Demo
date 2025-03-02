package com.praveen.picsumapp.di

import com.praveen.picsumapp.domain.usecase.GetPicSumImagesUseCase
import com.praveen.picsumapp.presentation.viewmodel.PicSumViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun providePicSumViewModel(getPicsumImagesUseCase: GetPicSumImagesUseCase): PicSumViewModel {
        return PicSumViewModel(getPicsumImagesUseCase)
    }
}