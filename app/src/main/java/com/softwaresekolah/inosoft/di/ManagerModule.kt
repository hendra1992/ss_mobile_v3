package com.softwaresekolah.inosoft.di

import com.softwaresekolah.inosoft.data.core.manager.LocalManagerImpl
import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagerModule {
    @Binds
    @Singleton
    abstract fun bindLocalManager(localManagerImpl: LocalManagerImpl) : LocalManager
}