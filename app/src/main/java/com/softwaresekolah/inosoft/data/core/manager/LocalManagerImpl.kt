package com.softwaresekolah.inosoft.data.core.manager

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.softwaresekolah.inosoft.util.Constant
import com.softwaresekolah.inosoft.util.Constant.APP_DATASTORE
import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalManagerImpl @Inject constructor(
    private val application: Application,
) : LocalManager {
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

    override suspend fun saveAppExp() {
        application.dataStore.edit { settings ->
            settings[PreferencesKeys.APP_EXP] = true
        }
    }

    override fun readAppExp(): Flow<Boolean> {
          return application.dataStore.data.map { pref->
            pref[PreferencesKeys.APP_EXP] ?: false
        }
    }

    override suspend fun saveUserLogin() {
        application.dataStore.edit {settings ->
            settings[PreferencesKeys.USER_LOGIN] = true
        }
        application.dataStore.edit { settings ->
            settings.remove(PreferencesKeys.APP_EXP)
        }
    }
    override fun readUserLogin(): Flow<Boolean> {
        return application.dataStore.data.map { pref->
            pref[PreferencesKeys.USER_LOGIN] ?: false
        }
    }

    override suspend fun saveSoftwareId(id: String) {
        application.dataStore.edit {settings ->
            settings[PreferencesKeys.SOFTWARE_ID] = id
        }
    }

    override suspend fun getSoftwareId(): String? {
        return application.dataStore.data.map { pref->
            pref[PreferencesKeys.SOFTWARE_ID]
        }.first()
    }

    override fun readUserFCMToken(): Flow<String> {
        return application.dataStore.data.map { pref->
            pref[PreferencesKeys.FCM_TOKEN].toString()
        }
    }

    override suspend fun saveUserFCMToken(token: String) {
        application.dataStore.edit {settings ->
            settings[PreferencesKeys.FCM_TOKEN] = token
        }
    }

    override suspend fun removeUserLogin() {
        application.dataStore.edit { settings ->
            settings.remove(PreferencesKeys.USER_LOGIN)
            settings.remove(PreferencesKeys.APP_EXP)
            settings.remove(PreferencesKeys.DEPKODE)
            settings.remove(PreferencesKeys.ID_SISWA)
            settings.remove(PreferencesKeys.ID_DEP)
            settings.remove(PreferencesKeys.USER_ID)
        }
    }

    override suspend fun saveAccessJwt(token: String) {
        application.dataStore.edit { preferences ->
            preferences[PreferencesKeys.ACCESS_TOKEN] = token
        }
    }

    override suspend fun saveRefreshJwt(token: String) {
        application.dataStore.edit { preferences ->
            preferences[PreferencesKeys.REFRESH_TOKEN] = token
        }
    }

    override suspend fun saveUserId(userId: String) {
        application.dataStore.edit { settings ->
            settings[PreferencesKeys.USER_ID] = userId
        }
    }

    override suspend fun saveDepkode(depkode: String) {
        application.dataStore.edit { settings ->
            settings[PreferencesKeys.DEPKODE] = depkode
        }
    }

    override suspend fun saveIdDep(idDep: String) {
        application.dataStore.edit { settings ->
            settings[PreferencesKeys.ID_DEP] = idDep
        }
    }

    override suspend fun saveIdSiswa(idSiswa: String) {
        application.dataStore.edit { settings ->
            settings[PreferencesKeys.ID_SISWA] = idSiswa
        }
    }

    override suspend fun getUserId(): String? {
        return application.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.USER_ID]
        }.first()
    }

    override suspend fun getIdDep(): String? {
        return application.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.ID_DEP]
        }.first()
    }

    override suspend fun getIdSiswa(): String? {
        return application.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.ID_SISWA]
        }.first()
    }

    override suspend fun getDepkode(): String? {
        return application.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.DEPKODE]
        }.first()
    }

    override suspend fun getAccessJwt(): String? {
        return application.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.ACCESS_TOKEN]
        }.first()
    }

    override suspend fun getRefreshJwt(): String? {
        return application.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.REFRESH_TOKEN]
        }.first()
    }

    override suspend fun refreshToken(): String? {
        return application.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.REFRESH_TOKEN]
        }.first()
    }

    override suspend fun clearAllTokens() {
        application. dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.ACCESS_TOKEN)
            preferences.remove(PreferencesKeys.REFRESH_TOKEN)
        }
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = APP_DATASTORE)

private object PreferencesKeys{
    val APP_ENTRY = booleanPreferencesKey(Constant.APP_ENTRY)
    val APP_EXP = booleanPreferencesKey(Constant.APP_EXP)
    val USER_LOGIN = booleanPreferencesKey(Constant.USER_LOGIN)
    val USER_ID = stringPreferencesKey(Constant.USER_ID)
    val DEPKODE = stringPreferencesKey(Constant.DEPCODE)
    val SOFTWARE_ID = stringPreferencesKey(Constant.SOFTWARE_ID)
    val ACCESS_TOKEN = stringPreferencesKey(Constant.ACCESS_TOKEN_JWT)
    val REFRESH_TOKEN = stringPreferencesKey(Constant.REFRESH_TOKEN_JWT)
    val FCM_TOKEN = stringPreferencesKey(Constant.FCM_TOKEN)
    val ID_DEP = stringPreferencesKey(Constant.DEP_ID)
    val ID_SISWA = stringPreferencesKey(Constant.STUDENT_ID)

}