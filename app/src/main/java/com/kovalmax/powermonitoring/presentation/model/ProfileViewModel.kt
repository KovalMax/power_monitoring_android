package com.kovalmax.powermonitoring.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kovalmax.powermonitoring.domain.UserRepositoryInterface
import com.kovalmax.powermonitoring.intent.signin.GoogleAuthClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val client: GoogleAuthClient,
    private val userRepository: UserRepositoryInterface
) : ViewModel() {
    val state = MutableStateFlow(false)

    fun onTgChanged(newState: Boolean, userId: String?) {
        if (userId == null) {
            return
        }
        userRepository.updateTelegramUserSettings(userId, newState)
        state.value = newState
    }

    init {
        viewModelScope.launch {
            val settings = userRepository.userSettings(client.getSignedInUser()!!.id)
            state.value = settings!!.useTelegram
        }
    }
}