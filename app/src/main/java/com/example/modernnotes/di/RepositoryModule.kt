package com.example.modernnotes.di

import com.example.modernnotes.data.notes.local.repositories.NotesRepositoryImpl
import com.example.modernnotes.data.notes.local.user_preferences.UserPreferencesRepositoryImpl
import com.example.modernnotes.data.notes.remote.repositories.RemoteNotesRepositoryImpl
import com.example.modernnotes.domain.notes.repositories.NotesRepository
import com.example.modernnotes.domain.notes.repositories.RemoteNotesRepository
import com.example.modernnotes.domain.notes.repositories.UserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNotesRepository(notesRepositoryImpl: NotesRepositoryImpl): NotesRepository

    @Binds
    @Singleton
    abstract fun bindUserPreferencesRepository(userPreferencesRepositoryImpl: UserPreferencesRepositoryImpl): UserPreferencesRepository


    @Binds
    @Singleton
    abstract fun bindRemoteNotesRepository(remoteNotesRepositoryImpl: RemoteNotesRepositoryImpl): RemoteNotesRepository
}