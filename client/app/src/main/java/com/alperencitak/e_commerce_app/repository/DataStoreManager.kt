package com.alperencitak.e_commerce_app.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

    private val dataStore = context.dataStore

    companion object {
        private val USER_ID = stringPreferencesKey("USER_ID")
    }

    suspend fun saveUserId(userId: String){
        dataStore.edit { preferences ->
            preferences[USER_ID] = userId
        }
    }

    suspend fun getUserId(): String? {
        val preferences = dataStore.data.first()
        return preferences[USER_ID]
    }

    suspend fun clearUserId() {
        dataStore.edit { it.remove(USER_ID) }
    }

}