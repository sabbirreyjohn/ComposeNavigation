package com.androidrey.composenavigation.datasource

import com.androidrey.composenavigation.model.Profile
import com.androidrey.composenavigation.model.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL =
    "https://api.github.com"

val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit =
    Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL)
        .build()

interface UserApiInterface {
    @GET("users")
    suspend fun getusers(@Query("since") lastUserId: Int): List<User>

    @GET("users/{USER_NAME}")
    suspend fun getProfile(@Path("USER_NAME") username: String?): Profile
}

object UserApi {
    val userApiInterface: UserApiInterface by lazy {
        retrofit.create(UserApiInterface::class.java)
    }
}