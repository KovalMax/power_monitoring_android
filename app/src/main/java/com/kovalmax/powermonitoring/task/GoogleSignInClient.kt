package com.kovalmax.powermonitoring.task

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kovalmax.powermonitoring.R

fun getGoogleSignInClient(context: Context): GoogleSignInClient {
    val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestId()
        .requestProfile()
        .requestIdToken(context.getString(R.string.gcp_id))
        .build()

    return GoogleSignIn.getClient(context, signInOptions)
}