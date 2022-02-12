package com.androidrey.composenavigation.repository

import com.androidrey.composenavigation.datasource.TheDatabase
import com.androidrey.composenavigation.datasource.UserApi
import com.androidrey.composenavigation.model.Profile
import com.androidrey.composenavigation.model.User
import com.androidrey.composenavigation.util.Status

class DataRepository(private val database: TheDatabase) {

    suspend fun getUsersFromServer(): Status<List<User>> {
        val response = try {
            UserApi.userApiInterface.getusers(0)
        } catch (e: Exception) {
            return Status.Error("An unknown error occured.")
        }
        return Status.Success(response)
    }

    suspend fun getProfileFromServer(username: String?): Status<Profile> {
        val response = try {
            UserApi.userApiInterface.getProfile(username)
        } catch (e: Exception) {
            return Status.Error("An unknown error occured.")
        }
        return Status.Success(response)
    }

    suspend fun getUsersFromDB(): Status<List<User>> {
        val response = try {
            database.userDao.getUsers()
        } catch (e: Exception) {
            return Status.Error("Failed to load from database")
        }
        if (response.isEmpty())
            return Status.Error("Database is empty")
        return Status.Success(response)
    }

    suspend fun insertUsersToDB(users: List<User>) = database.userDao.insertAll(users)
}