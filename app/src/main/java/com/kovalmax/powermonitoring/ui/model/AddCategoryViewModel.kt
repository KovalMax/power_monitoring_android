package com.kovalmax.powermonitoring.ui.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kovalmax.powermonitoring.domain.IApplicationRepository
import com.kovalmax.powermonitoring.domain.ICategoryRepository
import com.kovalmax.powermonitoring.domain.IDispatcherProvider
import com.kovalmax.powermonitoring.domain.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val categoryRepository: ICategoryRepository,
    private val applicationRepository: IApplicationRepository,
    private val contextProvider: IDispatcherProvider,
) : ViewModel(), CoroutineScope {
    private val jobTracker = Job()
    private lateinit var appId: String

    val category = MutableLiveData("")
    override val coroutineContext get() = contextProvider.provideIOContext(jobTracker)

    init {
        launch {
            appId = applicationRepository.getAppId()
        }
    }

    fun onCategoryChange(newCategory: String) {
        category.value = newCategory
    }

    fun onAddCategoryClick(category: String) {
        if (category.isEmpty()) {
            throw Exception()
        }

        val newCategory = Category(appId, category)
        categoryRepository.saveCategory(newCategory)
    }
}
