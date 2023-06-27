package com.kovalmax.powermonitoring.intent.signin

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val id: String,
    val email: String,
    val tokenId: String,
    val displayName: String?,
    val photoUrl: String?,
)

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)