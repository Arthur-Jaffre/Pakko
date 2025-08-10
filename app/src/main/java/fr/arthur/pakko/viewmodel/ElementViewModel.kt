package fr.arthur.pakko.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.arthur.pakko.models.CategorieUi
import fr.arthur.pakko.models.Element
import fr.arthur.pakko.models.ElementUi
import fr.arthur.pakko.usecase.ElementUseCase
import kotlinx.coroutines.launch

class ElementViewModel(
    private val elementUseCase: ElementUseCase
) : ViewModel() {
    private val _elements = MutableLiveData<List<Element>>()
    val elements: LiveData<List<Element>> = _elements

    private val _elementsByCategory = MutableLiveData<List<ElementUi>>()
    val elementsByCategory: LiveData<List<ElementUi>> = _elementsByCategory

    fun updateElementWithCategories(element: Element, selectedCategories: List<CategorieUi>) {
        viewModelScope.launch {
            elementUseCase.updateElementWithCategories(element, selectedCategories)
        }
    }

    fun getAllElements() {
        viewModelScope.launch {
            _elements.postValue(elementUseCase.getAllElements())
        }
    }

    fun getElementsForCategory(categorieId: String) {
        viewModelScope.launch {
            _elementsByCategory.postValue(elementUseCase.getElementsForCategory(categorieId))
        }
    }

    fun deleteElement(element: Element) {
        viewModelScope.launch {
            elementUseCase.deleteElement(element)
            getAllElements()
        }
    }
}