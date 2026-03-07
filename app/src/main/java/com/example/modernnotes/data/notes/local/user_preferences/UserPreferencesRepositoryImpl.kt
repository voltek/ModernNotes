package com.example.modernnotes.data.notes.local.user_preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.modernnotes.domain.notes.models.SortOrder
import com.example.modernnotes.domain.notes.repositories.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
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