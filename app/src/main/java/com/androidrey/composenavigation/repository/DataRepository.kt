package com.androidrey.composenavigation.repository

import com.androidrey.composenavigation.datasource.TheDatabase
import com.androidrey.composenavigation.datasource.UserApi
import com.androidrey.composenavigation.model.User

class DataRepository(private val database: TheDatabase) {
    suspend fun getUsersFromServer() = UserApi.userApiInterface.getusers(0)
    suspend fun getUsersFromDB() = database.userDao.getUsers()
    suspend fun insertUsersToDB(users: List<User>) = database.userDao.insertAll( users)
    suspend fun getProfileFromServer(username: String) =
        UserApi.userApiInterface.getProfile(username)
}