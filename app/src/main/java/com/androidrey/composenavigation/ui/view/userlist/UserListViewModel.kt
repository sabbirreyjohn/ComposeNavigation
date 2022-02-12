package com.androidrey.composenavigation.ui.view.userlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.androidrey.composenavigation.datasource.getDatabase
import com.androidrey.composenavigation.model.User
import com.androidrey.composenavigation.repository.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class UserListViewModel(application: Application) : AndroidViewModel(application) {

    val repo = DataRepository(getDatabase(application))

    private val _users = MutableStateFlow<List<User>>(mutableListOf())
    val users get() = _users

    init {
        loadUserData()
    }

    fun loadUserData() {
        viewModelScope.launch {
            try {
                val tempUsers = repo.getUsersFromServer()
                repo.insertUsersToDB(tempUsers)
                _users.emit(repo.getUsersFromDB())
            } catch (exception: IOException) {
                _users.emit(repo.getUsersFromDB())
            }
        }
    }
}