package com.softwaresekolah.inosoft.di

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.room.Room
import com.plcoding.internetconnectionobserver.AndroidConnectivityObserver
import com.plcoding.internetconnectionobserver.ConnectivityObserver
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import com.softwaresekolah.inosoft.BuildConfig
import com.softwaresekolah.inosoft.data.core.local.SoftwareSekolahDatabse
import com.softwaresekolah.inosoft.data.core.local.UserDao
import com.softwaresekolah.inosoft.util.Constant
import com.softwaresekolah.inosoft.data.core.remote.services.UserApiService
import com.softwaresekolah.inosoft.data.core.remote.services.AuthApiService
import com.softwaresekolah.inosoft.data.core.remote.services.RefreshTokenService
import com.softwaresekolah.inosoft.domain.auth.interceptor.AccessTokenInterceptor
import com.softwaresekolah.inosoft.domain.auth.interceptor.RefreshTokenInterceptor
import com.softwaresekolah.inosoft.domain.auth.usecase.AuthAuthenticator
import com.softwaresekolah.inosoft.util.SsBiometricPromptManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @[Provides Singleton]
    fun provideAuthApi(@PublicClient okHttpClient: OkHttpClient): AuthApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(AuthApiService::class.java)
    }

      @[Provides Singleton]
    fun provideBiometricPromptManager(@ApplicationContext activity: FragmentActivity): SsBiometricPromptManager {
        return SsBiometricPromptManager(activity)
    }

    @[Provides Singleton]
    fun provideInternetObserver(@ApplicationContext context: Context): ConnectivityObserver {
        return AndroidConnectivityObserver(context)
    }

    @[Provides Singleton]
    fun provideRefreshTokenApi(@TokenRefreshClient okHttpClient: OkHttpClient): RefreshTokenService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RefreshTokenService::class.java)
    }

    @[Provides Singleton]
    fun provideAuthenticatedApi(@AuthenticatedClient okHttpClient: OkHttpClient): UserApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(UserApiService::class.java)
    }

    @[Provides Singleton TokenRefreshClient]
    fun provideRefreshOkHttpClient(
        refreshTokenInterceptor: RefreshTokenInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(refreshTokenInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @[Provides Singleton AuthenticatedClient]
    fun provideAccessOkHttpClient(
        accessTokenInterceptor: AccessTokenInterceptor,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(accessTokenInterceptor)
            .authenticator(authAuthenticator)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @[Provides Singleton PublicClient]
    fun provideUnauthenticatedOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideSoftwareSekolahDatabase(
        application: Application
    ): SoftwareSekolahDatabse{
        return Room.databaseBuilder(
            context = application,
            klass = SoftwareSekolahDatabse::class.java,
            name = Constant.SSdbName
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(
        softwareSekolahDatabse: SoftwareSekolahDatabse
    ): UserDao = softwareSekolahDatabse.userDao
}