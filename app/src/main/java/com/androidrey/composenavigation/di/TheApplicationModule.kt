package com.androidrey.composenavigation.di


import android.content.Context
import com.androidrey.composenavigation.TheApplication
import com.androidrey.composenavigation.datasource.TheDatabase
import com.androidrey.composenavigation.datasource.UserApiHelper
import com.androidrey.composenavigation.datasource.UserApiHelperImpl
import com.androidrey.composenavigation.datasource.UserApiInterface
import com.androidrey.composenavigation.datasource.getDatabase
import com.androidrey.composenavigation.util.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TheApplicationModule {

    @Provides
    @Singleton
    fun getContext() = TheApplication.instance

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL).build()
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): TheDatabase =
        getDatabase(context)

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApiInterface =
        retrofit.create(UserApiInterface::class.java)

    @Provides
    @Singleton
    fun provideUserApiHelper(userApiHelperImpl: UserApiHelperImpl): UserApiHelper =
        userApiHelperImpl


    @Provides
    fun provideUserDao(database: TheDatabase) = database.userDao
}
