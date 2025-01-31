package com.softwaresekolah.inosoft.di

import com.softwaresekolah.inosoft.data.core.repository.ConfigAppRepositoryImpl
import com.softwaresekolah.inosoft.data.core.repository.UserRepositoryImpl
import com.softwaresekolah.inosoft.data.notification.repository.NotificationRepositoryImpl
import com.softwaresekolah.inosoft.data.profile.repository.FormDataProfileRepositoryImpl
import com.softwaresekolah.inosoft.data.settings.repository.SettingRepositoryImpl
import com.softwaresekolah.inosoft.domain.core.repository.ConfigAppRepository
import com.softwaresekolah.inosoft.domain.core.repository.UserRepository
import com.softwaresekolah.inosoft.domain.notification.repository.NotificationRepository
import com.softwaresekolah.inosoft.domain.profile.repository.FormDataProfileRepository
import com.softwaresekolah.inosoft.domain.settings.repository.SettingRepository
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
    abstract fun bindUserRepository(userRepository: UserRepositoryImpl) : UserRepository

    @Binds
    @Singleton
    abstract fun bindFormDataProfileRepository(formDataProfileRepository: FormDataProfileRepositoryImpl) : FormDataProfileRepository

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(notificationRepository: NotificationRepositoryImpl) : NotificationRepository

    @Binds
    @Singleton
    abstract fun bindSettingRepository(settingRepository: SettingRepositoryImpl) : SettingRepository
}