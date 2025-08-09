package fr.arthur.pakko.usecase

import fr.arthur.pakko.models.Category
import fr.arthur.pakko.models.Element
import fr.arthur.pakko.repositories.ElementRepository
import fr.arthur.pakko.room.entities.ElementCategorieEntityCrossRef

class ElementUseCase(
    private val elementRepository: ElementRepository
) {
    suspend fun getAllElements(): List<Element> {
        return elementRepository.getAllElements()
    }

    suspend fun getElementsByCategory(category: Category): List<Element> {
        return elementRepository.getElementsByCategory(category)
    }

    suspend fun insertElementWithCrossRefs(
        element: Element,
        crossRefs: List<ElementCategorieEntityCrossRef>
    ) {
        elementRepository.insertElementWithCrossRefs(element, crossRefs)
    }

    suspend fun deleteElement(element: Element) {
        elementRepository.deleteElement(element)
    }

}