package com.softwaresekolah.inosoft.domain.auth.usecase

import com.softwaresekolah.inosoft.data.auth.response.LoginDataResponse
import com.softwaresekolah.inosoft.data.core.local.UserDao
import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import com.softwaresekolah.inosoft.domain.core.models.User
import com.softwaresekolah.inosoft.domain.core.repository.UserRepository
import timber.log.Timber
import javax.inject.Inject

class SaveUserLogin @Inject constructor(
    private val localManager: LocalManager,
    private val userRepository: UserRepository
){
    suspend operator fun invoke(depkode: String, token: String, username: String, data: LoginDataResponse){
        localManager.saveDepkode(depkode)
        localManager.saveUserId(data.usr_id.toString())
        localManager.saveAccessJwt(data.auth_token)
        localManager.saveRefreshJwt(data.refresh_token)
        localManager.saveIdDep(data.id_dep)
        localManager.saveIdSiswa(data.id_siswa)
        userRepository.upsertUser(User(
            idSiswa = data.id_siswa,
            depkode = depkode,
            accessToken = data.auth_token,
            refreshToken = data.refresh_token,
            siswaNama = data.siswa_nama,
            imageUrl = data.profile_photo_path,
            userId = data.usr_id.toString(),
            fcmToken = token,
            idDep = data.id_dep,
            username = username
        ))
        localManager.saveUserLogin()
    }
}