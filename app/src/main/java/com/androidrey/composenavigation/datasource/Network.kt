package com.androidrey.composenavigation.datasource

import com.androidrey.composenavigation.model.Profile
import com.androidrey.composenavigation.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject


interface UserApiInterface {
    @GET("users")
    suspend fun getusers(@Query("since") lastUserId: Int): List<User>

    @GET("users/{USER_NAME}")
    suspend fun getProfile(@Path("USER_NAME") username: String?): Profile
}


class UserApiHelperImpl @Inject internal constructor(private val userApiInterface: UserApiInterface) :
    UserApiHelper {
    override suspend fun getUsers(lastUserId: Int): List<User> {
        return userApiInterface.getusers(lastUserId)
    }

    override suspend fun getProfile(username: String?): Profile {
        return userApiInterface.getProfile(username)
    }


}

interface UserApiHelper {
    suspend fun getUsers(lastUserId: Int): List<User>
    suspend fun getProfile(username: String?): Profile
}