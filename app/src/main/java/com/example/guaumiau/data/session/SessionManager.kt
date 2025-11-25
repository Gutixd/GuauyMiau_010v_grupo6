package com.example.guaumiau.data.session

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//para guardar datos simples de sesion
private val Context.dataStore by preferencesDataStore(name = "session_store")

class SessionManager(private val context: Context) {
    // llave para mejorar el id del usuario 
    private object Keys { val USER_ID = longPreferencesKey("user_id") }

    val currentUserId: Flow<Long?> = context.dataStore.data.map { prefs: Preferences ->
        prefs[Keys.USER_ID]
    }

    // guarda o eliminael id del usuario segun correspoinda
    suspend fun setCurrentUser(id: Long?) {
        context.dataStore.edit { prefs ->
            if (id == null) prefs.remove(Keys.USER_ID) else prefs[Keys.USER_ID] = id
        }
    }
}
