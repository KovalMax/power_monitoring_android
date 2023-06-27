package com.kovalmax.powermonitoring.persistence

import com.google.firebase.firestore.FirebaseFirestore
import com.kovalmax.powermonitoring.domain.UserRepositoryInterface
import com.kovalmax.powermonitoring.domain.model.User
import com.kovalmax.powermonitoring.domain.model.UserSettings
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val firestore: FirebaseFirestore
) : UserRepositoryInterface {
    private val collectionName = "Users"
    private val settingsCollectionName = "UserSettings"

    override fun createNewUser(user: User) {
        firestore
            .collection(collectionName)
            .document(user.id)
            .set(user)

        firestore
            .collection(settingsCollectionName)
            .document(user.id)
            .set(UserSettings(userId = user.id))
    }

    override suspend fun checkIfUserExists(userId: String): Boolean {
        val result = firestore
            .collection(collectionName)
            .document(userId)
            .get()
            .await()
            .toObject(User::class.java)

        return result != null
    }

    override suspend fun userSettings(userId: String): UserSettings? {
        return firestore.collection(settingsCollectionName)
            .document(userId)
            .get()
            .await()
            .toObject(UserSettings::class.java)
    }

    override fun updateTelegramUserSettings(userId: String, useTg: Boolean) {
        firestore
            .collection(settingsCollectionName)
            .document(userId)
            .update("useTelegram", useTg)
    }

}