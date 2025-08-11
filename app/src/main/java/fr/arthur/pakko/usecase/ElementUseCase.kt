package fr.arthur.pakko.usecase

import fr.arthur.pakko.models.Element
import fr.arthur.pakko.repositories.ElementRepository

class ElementUseCase(
    private val elementRepository: ElementRepository
) {
    suspend fun insertElement(element: Element) {
        elementRepository.insertElement(element)
    }

    suspend fun updateElement(element: Element) {
        elementRepository.updateElement(element)
    }

    suspend fun deleteElement(element: Element) {
        elementRepository.deleteElement(element)
    }

    suspend fun getAllElements(): List<Element> {
        return elementRepository.getAllElements()
    }

}