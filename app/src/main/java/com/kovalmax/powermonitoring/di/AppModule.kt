package com.kovalmax.powermonitoring.di

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.kovalmax.powermonitoring.BaseApplication
import com.kovalmax.powermonitoring.intent.signin.GoogleAuthClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideGoogleAuthClient(@ApplicationContext app: Context): GoogleAuthClient {
        return GoogleAuthClient(
            context = app,
            oneTapClient = Identity.getSignInClient(app)
        )
    }
}