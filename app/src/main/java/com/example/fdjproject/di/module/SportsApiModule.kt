package com.example.fdjproject.di.module

import com.example.fdjproject.data.services.SportsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SportsApiModule {

    @Provides
    @Singleton
    fun provideSportsApi(retrofit: Retrofit): SportsApiService {
        return retrofit.create(SportsApiService::class.java)
    }
}