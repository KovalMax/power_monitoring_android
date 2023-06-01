package com.kovalmax.powermonitoring.ui.model

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.kovalmax.powermonitoring.domain.IDispatcherProvider
import com.kovalmax.powermonitoring.domain.UserRepositoryInterface
import com.kovalmax.powermonitoring.domain.model.User
import com.kovalmax.powermonitoring.persistence.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val contextProvider: IDispatcherProvider,
    private val userRepository: UserRepositoryInterface,
) : ViewModel(), CoroutineScope {
    private val jobTracker: CompletableJob = Job()
    override val coroutineContext get() = contextProvider.provideIOContext(jobTracker)
    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user: StateFlow<User?> = _user

    suspend fun signIn(
        email: String,
        displayName: String,
        photoUrl: Uri,
        id: String,
        tokenId: String
    ) {
        val user = User(email, displayName, photoUrl.toString(), id, tokenId)
        if (!userRepository.checkIfUserExists(id)) {
            userRepository.createNewUser(user)
        }

        _user.value = user
    }
}