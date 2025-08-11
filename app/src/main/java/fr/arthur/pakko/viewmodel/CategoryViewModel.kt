package fr.arthur.pakko.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.arthur.pakko.models.Category
import fr.arthur.pakko.models.Element
import fr.arthur.pakko.models.ElementCategory
import fr.arthur.pakko.usecase.CategoryUseCase
import fr.arthur.pakko.usecase.ElementCategoryUseCase
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryUseCase: CategoryUseCase,
    private val elementCategoryUseCase: ElementCategoryUseCase
) : ViewModel() {
    private val _categories = MutableLiveData<List<Category>>()
    val allCategories: LiveData<List<Category>> = _categories

    private val _categoriesByElement = MutableLiveData<List<ElementCategory>>()
    val categoriesByElement: LiveData<List<ElementCategory>> = _categoriesByElement

    fun getAllCategories() {
        viewModelScope.launch {
            _categories.value = categoryUseCase.getAllCategories()
        }
    }

    fun getCategoriesByElement(element: Element) {
        viewModelScope.launch {
            _categoriesByElement.value = elementCategoryUseCase.getCategoriesByElement(element)
        }
    }

    fun insertCategory(category: Category) {
        viewModelScope.launch {
            categoryUseCase.insertCategory(category)
            getAllCategories()
        }
    }

    fun updateCategory(category: Category) {
        viewModelScope.launch {
            categoryUseCase.updateCategory(category)
            getAllCategories()
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            // supprimer les éléments de la catégorie
            elementCategoryUseCase.deleteAllElementFromCategory(category)
            // supprimer la catégorie
            categoryUseCase.deleteCategory(category)
            getAllCategories()
        }
    }
}

