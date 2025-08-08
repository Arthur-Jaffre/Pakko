package fr.arthur.pakko.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.arthur.pakko.models.CategorieUi
import fr.arthur.pakko.models.Category
import fr.arthur.pakko.usecase.CategoryUseCase
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryUseCase: CategoryUseCase
) : ViewModel() {
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _categoriesForElement = MutableLiveData<List<Category>>()
    val categoriesForElement: LiveData<List<Category>> = _categoriesForElement
    private val _error = MutableLiveData<Throwable?>()
    val error: LiveData<Throwable?> = _error

    fun getAllCategories() {
        viewModelScope.launch {
            _categories.value = categoryUseCase.getAllCategories()
        }
    }

    fun updateCategories(category: Category) {
        viewModelScope.launch {
            try {
                categoryUseCase.updateCategory(category)
                _error.value = null
                getAllCategories()
            } catch (e: Exception) {
                _error.value = e
            }
        }
    }

    fun deleteCategories(category: Category) {
        viewModelScope.launch {
            categoryUseCase.deleteCategory(category)
            getAllCategories()
        }
    }

    fun insertCategories(category: Category) {
        viewModelScope.launch {
            try {
                categoryUseCase.insertCategory(category)
                _error.value = null
                getAllCategories()
            } catch (e: Exception) {
                _error.value = e
            }

        }
    }

    fun getCategoriesForElement(elementId: String) {
        viewModelScope.launch {
            _categoriesForElement.postValue(categoryUseCase.getCategoriesForElement(elementId))
        }
    }

    fun updateElementCategory(categoryUI: CategorieUi, elementId: String) {
        viewModelScope.launch {
            categoryUseCase.updateElementCategory(categoryUI, elementId)
        }
    }
}

