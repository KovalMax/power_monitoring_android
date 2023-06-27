package com.kovalmax.powermonitoring.presentation.model

import androidx.lifecycle.ViewModel
import com.kovalmax.powermonitoring.domain.UserRepositoryInterface
import com.kovalmax.powermonitoring.domain.model.User
import com.kovalmax.powermonitoring.intent.signin.SignInResult
import com.kovalmax.powermonitoring.intent.signin.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val userRepository: UserRepositoryInterface) :
    ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    suspend fun onSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }

        if (result.data == null) {
            return
        }

        val userData = result.data
        if (!userRepository.checkIfUserExists(userData.id)) {
            userRepository.createNewUser(
                User(
                    email = userData.email,
                    tokenId = userData.tokenId,
                    id = userData.id,
                    photoUrl = userData.photoUrl,
                    displayName = userData.displayName
                )
            )
        }
    }

    fun resetState() {
        _state.update { SignInState() }
    }
}