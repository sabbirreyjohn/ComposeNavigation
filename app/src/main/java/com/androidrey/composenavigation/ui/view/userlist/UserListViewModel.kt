package com.androidrey.composenavigation.ui.view.userlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.androidrey.composenavigation.datasource.getDatabase
import com.androidrey.composenavigation.model.User
import com.androidrey.composenavigation.repository.DataRepository
import com.androidrey.composenavigation.util.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class UserListViewModel(application: Application) : AndroidViewModel(application) {

    val repo = DataRepository(getDatabase(application))

    private val _users = MutableStateFlow<List<User>>(mutableListOf())
    val users get() = _users

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading get() = _isLoading

    private val _hasError = MutableStateFlow<Boolean>(false)
    val hasError get() = _hasError

    init {
        loadUserData()
    }

    fun loadUserData() {
        viewModelScope.launch {
            _isLoading.value = true
            val status = repo.getUsersFromServer()
            when (status) {
                is Status.Success -> {
                    repo.insertUsersToDB(status.data!!)
                    loadUserListFromDB()
                }
                is Status.Error -> {
                    loadUserListFromDB()
                }

            }
        }
    }

    private suspend fun loadUserListFromDB() {
        val dbStatus = repo.getUsersFromDB()
        _isLoading.value = false
        when (dbStatus) {
            is Status.Success -> {
                _hasError.value = false
                _users.value = dbStatus.data!!
            }
            is Status.Error -> {
                _hasError.value = true
                _users.value = mutableListOf()
            }
        }
    }
}