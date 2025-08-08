package fr.arthur.pakko.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.arthur.pakko.models.Element
import fr.arthur.pakko.room.entities.ElementCategorieEntityCrossRef
import fr.arthur.pakko.usecase.ElementUseCase
import kotlinx.coroutines.launch

class ElementViewModel(
    private val elementUseCase: ElementUseCase
) : ViewModel() {
    private val _elements = MutableLiveData<List<Element>>()
    val elements: LiveData<List<Element>> = _elements

    fun getAllElements() {
        viewModelScope.launch {
            _elements.value = elementUseCase.getAllElements()
        }
    }

    fun insertElementWithCrossRefs(
        element: Element,
        crossRefs: List<ElementCategorieEntityCrossRef>
    ) {
        viewModelScope.launch {
            elementUseCase.insertElementWithCrossRefs(element, crossRefs)
            getAllElements()
        }
    }

    fun deleteElement(element: Element) {
        viewModelScope.launch {
            elementUseCase.deleteElement(element)
            getAllElements()
        }
    }
}