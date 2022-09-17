package dev.logickoder.exhibits.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dev.logickoder.exhibits.data.model.Exhibit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object ExhibitsDataStore {
    private val EXHIBITS = stringPreferencesKey("exhibits")
    private val Context.local: DataStore<Preferences> by preferencesDataStore(
        name = "exhibits"
    )

    suspend fun save(data: List<Exhibit>, context: Context) {
        context.local.edit { preferences ->
            preferences[EXHIBITS] = Json.encodeToString(data)
        }
    }

    fun get(context: Context): Flow<List<Exhibit>> {
        return context.local.data.map { preferences ->
            preferences[EXHIBITS]?.let {
                Json.decodeFromString<List<Exhibit>>(it)
            } ?: emptyList()
        }
    }
}