package com.example.tudijit2.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserStore(private val context: Context) {


    // to make sure there is only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserInfo")
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        val USER_PASS_KEY = stringPreferencesKey("user_pass")
    }
    suspend fun saveUser(mail: String,passWord:String) {
        context.dataStore.edit { preferences ->
            preferences[USER_EMAIL_KEY] = mail
            preferences[USER_PASS_KEY] = passWord
        }
    }
    // to get the email
    val getEmail: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_EMAIL_KEY] ?: ""
        }
    val getPass: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_PASS_KEY] ?: ""
        }

    // to save the email

}