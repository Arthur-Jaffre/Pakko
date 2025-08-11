package fr.arthur.pakko.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.arthur.pakko.models.Category
import fr.arthur.pakko.models.Element
import fr.arthur.pakko.models.ElementCategory
import fr.arthur.pakko.usecase.ElementCategoryUseCase
import fr.arthur.pakko.usecase.ElementUseCase
import kotlinx.coroutines.launch

class ElementViewModel(
    private val elementUseCase: ElementUseCase,
    private val elementCategoryUseCase: ElementCategoryUseCase
) : ViewModel() {
    private val _elements = MutableLiveData<List<Element>>()
    val allElements: LiveData<List<Element>> = _elements

    private val _elementsByCategory = MutableLiveData<List<ElementCategory>>()
    val elementsByCategory: LiveData<List<ElementCategory>> = _elementsByCategory

    fun getAllElements() {
        viewModelScope.launch {
            _elements.value = elementUseCase.getAllElements()
        }
    }

    fun initElementsByCategory() {
        _elementsByCategory.value = emptyList()
    }

    fun getElementsByCategory(category: Category) {
        viewModelScope.launch {
            _elementsByCategory.value = elementCategoryUseCase.getElementsByCategory(category)
        }
    }

    fun updateElementCategory(elementCategory: ElementCategory) {
        viewModelScope.launch {
            elementCategoryUseCase.updateElementCategory(elementCategory)
            getElementsByCategory(elementCategory.category)
        }
    }


    // destruction du fragment parent, donc obliger : suspend
    suspend fun updateElementWithCategories(
        element: Element,
        newCategories: List<Category>,
        initialElementCategories: List<ElementCategory>
    ) {
        elementUseCase.updateElement(element)
        elementCategoryUseCase.updateElementCategories(
            element,
            newCategories,
            initialElementCategories
        )
    }

    fun deleteElementFromCategory(elementCategory: ElementCategory) {
        viewModelScope.launch {
            elementCategoryUseCase.deleteElementCategory(elementCategory)
            getElementsByCategory(elementCategory.category)
        }
    }


    suspend fun deleteElementWithCategories(
        element: Element,
        initialElementCategory: List<ElementCategory>
    ) {
        initialElementCategory.forEach { elementCategory ->
            elementCategoryUseCase.deleteElementCategory(elementCategory)
        }
        elementUseCase.deleteElement(element)

    }

    fun addElementByCategory(element: Element, category: Category) {
        viewModelScope.launch {
            elementUseCase.insertElement(element)
            elementCategoryUseCase.insertElementCategory(
                ElementCategory(element, category)
            )
            getElementsByCategory(category)
        }
    }

    fun addElementWithCategories(element: Element, categories: List<Category>) {
        viewModelScope.launch {
            elementUseCase.insertElement(element)
            categories.forEach { category ->
                elementCategoryUseCase.insertElementCategory(
                    ElementCategory(element, category)
                )
            }
        }
    }
}