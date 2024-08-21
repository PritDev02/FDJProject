package com.example.fdjproject.di.module

import com.example.fdjproject.data.repository.SportRepositoryImpl
import com.example.fdjproject.domain.repository.SportsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindSportsRepositoryImpl(impl: SportRepositoryImpl): SportsRepository
}