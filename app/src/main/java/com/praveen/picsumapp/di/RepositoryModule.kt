package com.praveen.picsumapp.di

import com.praveen.picsumapp.data.remote.PicSumApiService
import com.praveen.picsumapp.data.repository.PicSumRepositoryImpl
import com.praveen.picsumapp.domain.repository.PicSumRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePicSumRepository(api: PicSumApiService): PicSumRepository {
        return PicSumRepositoryImpl(api)
    }
}