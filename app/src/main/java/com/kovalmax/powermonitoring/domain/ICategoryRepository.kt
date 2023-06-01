package com.kovalmax.powermonitoring.domain

import com.kovalmax.powermonitoring.domain.model.Category

interface ICategoryRepository {
    fun saveCategory(category: Category)

    suspend fun getCategories(applicationId: String): List<Category>
}