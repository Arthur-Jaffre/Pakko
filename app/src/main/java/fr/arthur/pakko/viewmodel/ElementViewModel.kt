package fr.arthur.pakko.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.arthur.pakko.models.CategorieUi
import fr.arthur.pakko.models.Category
import fr.arthur.pakko.models.Element
import fr.arthur.pakko.usecase.ElementUseCase
import kotlinx.coroutines.launch

class ElementViewModel(
    private val elementUseCase: ElementUseCase
) : ViewModel() {
    private val _elements = MutableLiveData<List<Element>>()
    val elements: LiveData<List<Element>> = _elements

    private val _elementsByCategory = MutableLiveData<List<Element>>()
    val elementsByCategory: LiveData<List<Element>> = _elementsByCategory

    fun updateElementWithCategories(element: Element, selectedCategories: List<CategorieUi>) {
        viewModelScope.launch {
            elementUseCase.updateElementWithCategories(element, selectedCategories)
        }
    }

    fun getAllElements() {
        viewModelScope.launch {
            _elements.value = elementUseCase.getAllElements()
        }
    }

    fun getElementsByCategory(category: Category) {
        viewModelScope.launch {
            _elementsByCategory.value = elementUseCase.getElementsByCategory(category)
        }
    }

    fun deleteElement(element: Element) {
        viewModelScope.launch {
            elementUseCase.deleteElement(element)
            getAllElements()
        }
    }
}