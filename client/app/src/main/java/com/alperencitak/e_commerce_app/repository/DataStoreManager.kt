package com.alperencitak.e_commerce_app.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
        private val FAVORITE_PRODUCTS = stringPreferencesKey("FAVORITE_PRODUCTS")
    }

    private suspend fun saveFavorites(favorites: List<String>) {
        val json = Gson().toJson(favorites)
        dataStore.edit { preferences ->
            preferences[FAVORITE_PRODUCTS] = json
        }
    }

    suspend fun getFavorites(): List<String>{
        val preferences = dataStore.data.first()
        val json = preferences[FAVORITE_PRODUCTS] ?: "[]"
        return Gson().fromJson(json, object : TypeToken<List<String>>() {}.type)
    }

    suspend fun addFavorite(productId: String){
        val favorites = getFavorites().toMutableList()
        if (!favorites.contains(productId)){
            favorites.add(productId)
            saveFavorites(favorites)
        }
    }

    suspend fun removeFavorites(productId: String){
        val favorites = getFavorites().toMutableList()
        if (favorites.contains(productId)){
            favorites.remove(productId)
            saveFavorites(favorites)
        }
    }

    suspend fun saveUserId(userId: String){
        dataStore.edit { preferences ->
            preferences[USER_ID] = userId
        }
    }

    suspend fun getUserId(): String {
        val preferences = dataStore.data.first()
        return preferences[USER_ID] ?: "0"
    }

    suspend fun clearUserId() {
        dataStore.edit { it.remove(USER_ID) }
    }

}