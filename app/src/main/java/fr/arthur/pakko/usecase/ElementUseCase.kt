package fr.arthur.pakko.usecase

import fr.arthur.pakko.models.CategorieUi
import fr.arthur.pakko.models.Element
import fr.arthur.pakko.models.ElementUi
import fr.arthur.pakko.repositories.ElementRepository

class ElementUseCase(
    private val elementRepository: ElementRepository
) {
    suspend fun getAllElements(): List<Element> {
        return elementRepository.getAllElements()
    }

    suspend fun updateElementWithCategories(
        element: Element,
        selectedCategories: List<CategorieUi>
    ) {
        elementRepository.updateElementWithCategories(element, selectedCategories)
    }

    suspend fun getElementsForCategory(categorieId: String): List<ElementUi> {
        return elementRepository.getElementsForCategory(categorieId)
    }


    suspend fun deleteElement(element: Element) {
        elementRepository.deleteElement(element)
    }

}