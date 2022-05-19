package com.androidrey.composenavigation.ui.view.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidrey.composenavigation.model.Profile
import com.androidrey.composenavigation.repository.DataRepository
import com.androidrey.composenavigation.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repo: DataRepository) :
    ViewModel() {

    private var _profile = MutableStateFlow(Profile())
    val profile get() = _profile

    private val _hasError = MutableStateFlow(false)
    val hasError get() = _hasError

     fun loadProfile(userName: String?) {
        viewModelScope.launch {
            val status = repo.getProfileFromServer(userName)
            when (status) {
                is Status.Success -> {
                    _hasError.value = false
                    _profile.value = status.data!!
                }
                is Status.Error -> {
                    _hasError.value = true
                    _profile.value = Profile()
                }

            }
        }
    }
}
