package com.example.modernnotes.di

import com.example.modernnotes.data.util.SystemTimeProvider
import com.example.modernnotes.domain.util.TimeProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilModule {

    @Binds
    @Singleton
    abstract fun bindTimeProvider(systemTimeProvider: SystemTimeProvider): TimeProvider

}