package com.softwaresekolah.inosoft.domain.core.usecase

import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import com.softwaresekolah.inosoft.domain.core.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SwitchAccount @Inject constructor(
    private val localManager: LocalManager
) {
    suspend operator fun invoke(user: User): String {
        localManager.saveUserFCMToken(user.fcmToken)
        localManager.saveAccessJwt(user.accessToken)
        localManager.saveRefreshJwt(user.refreshToken)
        localManager.saveUserId(user.userId)
        localManager.saveIdSiswa(user.idSiswa)
        localManager.saveIdDep(user.idDep)
        localManager.saveDepkode(user.depkode)
        localManager.saveUserLogin()
        return "Beralih ke ${user.siswaNama}"
    }
}