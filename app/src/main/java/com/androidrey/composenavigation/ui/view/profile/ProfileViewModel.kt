package com.androidrey.composenavigation.ui.view.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.androidrey.composenavigation.datasource.getDatabase
import com.androidrey.composenavigation.model.Profile
import com.androidrey.composenavigation.model.User
import com.androidrey.composenavigation.repository.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application, userName: String?) :
    AndroidViewModel(application) {

    val repo = DataRepository(getDatabase(application))
    private var _profile = MutableStateFlow<Profile>(Profile())
    val profile get() = _profile

    init {
        loadProfile(userName)
    }

    private fun loadProfile(userName: String?) {
        viewModelScope.launch {
            try {
                val tempProfile = repo.getProfileFromServer(userName)
                _profile.emit(tempProfile)

            } catch (networkError: Exception) {
                networkError.printStackTrace()
            }
        }
    }

    class ProfileViewModelFactory(
        private val mApplication: Application,
        private val userName: String?
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                return ProfileViewModel(mApplication, userName) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
