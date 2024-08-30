package com.softwaresekolah.inosoft.domain.core.usecase

import com.softwaresekolah.inosoft.domain.core.models.User
import com.softwaresekolah.inosoft.domain.core.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUser @Inject constructor(
    private val userRepository: UserRepository,
) {

    operator fun invoke(): Flow<List<User>> {
        return userRepository.getUsers()
    }
}