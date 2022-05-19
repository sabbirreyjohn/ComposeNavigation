package com.androidrey.composenavigation.ui.view.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidrey.composenavigation.model.User
import com.androidrey.composenavigation.repository.DataRepository
import com.androidrey.composenavigation.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val repo: DataRepository) : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(mutableListOf())
    val users get() = _users

    private val _isLoading = MutableStateFlow(false)
    val isLoading get() = _isLoading

    private val _hasError = MutableStateFlow(false)
    val hasError get() = _hasError

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val status = repo.getUsersFromServer()) {
                is Status.Success -> {
                    repo.insertUsersToDB(status.data!!)
                    loadUserListFromDB()
                }
                is Status.Error -> {
                    loadUserListFromDB()
                }
                else -> {

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