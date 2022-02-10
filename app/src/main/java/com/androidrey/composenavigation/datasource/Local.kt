package com.androidrey.composenavigation.datasource

import android.content.Context
import androidx.room.*
import com.androidrey.composenavigation.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("select * from User")
    fun getUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)
}

@Database(entities = [User::class], version = 1)

abstract class TheDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}

private lateinit var INSTANCE: TheDatabase

fun getDatabase(context: Context): TheDatabase {
    synchronized(TheDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                TheDatabase::class.java,
                "users.db"
            ).build()
        }
    }
    return INSTANCE
}