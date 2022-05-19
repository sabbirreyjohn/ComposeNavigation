package com.androidrey.composenavigation.repository

import com.androidrey.composenavigation.datasource.TheDatabase
import com.androidrey.composenavigation.datasource.UserApiHelper
import com.androidrey.composenavigation.model.Profile
import com.androidrey.composenavigation.model.User
import com.androidrey.composenavigation.util.Status
import javax.inject.Inject

class DataRepository @Inject internal constructor(
    private val database: TheDatabase,
    private val userApiHelper: UserApiHelper
) {

    suspend fun getUsersFromServer(): Status<List<User>> {
        val response = try {
            userApiHelper.getUsers(0)
        } catch (e: Exception) {
            return Status.Error("An unknown error occured.")
        }
        return Status.Success(response)
    }

    suspend fun getProfileFromServer(username: String?): Status<Profile> {
        val response = try {
            userApiHelper.getProfile(username)
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