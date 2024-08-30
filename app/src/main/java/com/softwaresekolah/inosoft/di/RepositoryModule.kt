package com.softwaresekolah.inosoft.di

import com.softwaresekolah.inosoft.data.core.repository.ConfigAppRepositoryImpl
import com.softwaresekolah.inosoft.data.core.repository.UserRepositoryImpl
import com.softwaresekolah.inosoft.domain.core.repository.ConfigAppRepository
import com.softwaresekolah.inosoft.domain.core.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindConfigAppRepository(configAppRepository: ConfigAppRepositoryImpl) : ConfigAppRepository

    @Binds
    @Singleton
    abstract fun bindUserAppRepository(userRepository: UserRepositoryImpl) : UserRepository
}