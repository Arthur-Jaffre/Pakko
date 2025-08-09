package fr.arthur.pakko.usecase

import fr.arthur.pakko.models.CategorieUi
import fr.arthur.pakko.models.Category
import fr.arthur.pakko.models.Element
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

    suspend fun getElementsByCategory(category: Category): List<Element> {
        return elementRepository.getElementsByCategory(category)
    }

    suspend fun deleteElement(element: Element) {
        elementRepository.deleteElement(element)
    }

}