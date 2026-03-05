package com.example.modernnotes.data.note.user_preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.modernnotes.domain.models.SortOrder
import com.example.modernnotes.domain.repositories.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepositoryImpl(private val dataStore: DataStore<Preferences>) :
    UserPreferencesRepository {

    override val sortOrderFlow: Flow<SortOrder>
        get() = dataStore.data.map { preferences ->
            val sortOrderName = preferences[SORT_ORDER] ?: SortOrder.DATE_DESC.name

            try {
                SortOrder.valueOf(sortOrderName)
            } catch (_: IllegalArgumentException) {
                SortOrder.DATE_DESC
            }
        }

    override suspend fun setSortOrder(sortOrder: SortOrder) {
        dataStore.edit { preferences ->
            preferences[SORT_ORDER] = sortOrder.name
        }
    }

    companion object {
        private val SORT_ORDER = stringPreferencesKey("sort_order")
    }
}