package com.kovalmax.powermonitoring.persistence

import com.google.firebase.firestore.FirebaseFirestore
import com.kovalmax.powermonitoring.domain.ICategoryRepository
import com.kovalmax.powermonitoring.domain.model.Category
import kotlinx.coroutines.tasks.await
import java.util.*

class CategoryRepository(
    private val firestore: FirebaseFirestore
) : ICategoryRepository {
    private val collectionName = "categories"
    private val appIdFieldName = "applicationId"

    override fun saveCategory(category: Category) {
        firestore
            .collection(collectionName)
            .document(UUID.randomUUID().toString())
            .set(category)
    }

    override suspend fun getCategories(applicationId: String): List<Category> {
        return firestore.collection(collectionName)
            .whereEqualTo(appIdFieldName, applicationId)
            .get()
            .await()
            .toObjects(Category::class.java)
    }
}