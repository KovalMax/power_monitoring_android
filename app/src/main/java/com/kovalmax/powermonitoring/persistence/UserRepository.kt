package com.kovalmax.powermonitoring.persistence

import com.google.firebase.firestore.FirebaseFirestore
import com.kovalmax.powermonitoring.domain.UserRepositoryInterface
import com.kovalmax.powermonitoring.domain.model.User
import kotlinx.coroutines.tasks.await
import java.util.UUID

class UserRepository(
    private val firestore: FirebaseFirestore
) : UserRepositoryInterface {
    private val collectionName = "Users"
    private val userIdField = "id"

    override fun createNewUser(user: User) {
        firestore
            .collection(collectionName)
            .document(UUID.randomUUID().toString())
            .set(user)
    }

    override suspend fun checkIfUserExists(userId: String): Boolean {
        val result = firestore
            .collection(collectionName)
            .whereEqualTo(userIdField, userId)
            .limit(1)
            .get()
            .await()
            .toObjects(User::class.java)

        return result.isNotEmpty()
    }

}