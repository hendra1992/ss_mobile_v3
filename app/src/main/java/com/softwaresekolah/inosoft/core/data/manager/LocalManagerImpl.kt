package com.softwaresekolah.inosoft.core.data.manager

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.softwaresekolah.inosoft.core.Util.Constant
import com.softwaresekolah.inosoft.core.Util.Constant.APP_DATASTORE
import com.softwaresekolah.inosoft.core.Util.Constant.APP_ENTRY
import com.softwaresekolah.inosoft.core.domain.manager.LocalManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalManagerImpl @Inject constructor(
    private val application: Application
) : LocalManager{
    override suspend fun saveAppEntry() {
        application.dataStore.edit { settings ->
            settings[PreferencesKeys.APP_ENTRY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return application.dataStore.data.map { pref->
            pref[PreferencesKeys.APP_ENTRY] ?: false
        }
    }

    override suspend fun saveUserLogin() {
        application.dataStore.edit {settings ->
            settings[PreferencesKeys.USER_LOGIN] = true
        }
    }

    override fun readUserLogin(): Flow<Boolean> {
        return application.dataStore.data.map { pref->
            pref[PreferencesKeys.USER_LOGIN] ?: false
        }
    }

    override suspend fun removeUserLogin() {
        application.dataStore.edit { settings ->
            settings.remove(PreferencesKeys.USER_LOGIN)
        }
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = APP_DATASTORE)

private object PreferencesKeys{
    val APP_ENTRY = booleanPreferencesKey(Constant.APP_ENTRY)
    val USER_LOGIN = booleanPreferencesKey(Constant.USER_LOGIN)
}