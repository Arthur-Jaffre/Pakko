package fr.arthur.pakko.usecase

import fr.arthur.pakko.models.Category
import fr.arthur.pakko.models.Element
import fr.arthur.pakko.models.ElementCategory
import fr.arthur.pakko.repositories.ElementCategoryRepository

class ElementCategoryUseCase(
    private val elementCategoryRepository: ElementCategoryRepository
) {
    suspend fun insertElementCategory(elementCategory: ElementCategory) {
        elementCategoryRepository.insertElementCategory(elementCategory)
    }

    suspend fun deleteElementCategory(elementCategory: ElementCategory) {
        elementCategoryRepository.deleteElementCategory(elementCategory)
    }

    suspend fun updateElementCategory(elementCategory: ElementCategory) {
        elementCategoryRepository.updateElementCategory(elementCategory)
    }

    suspend fun updateElementCategories(
        element: Element,
        newCategories: List<Category>,
        initialElementCategories: List<ElementCategory>
    ) {
        val initialCategories = initialElementCategories.map { it.category }

        initialCategories.filter { it !in newCategories }.forEach { categoryToDelete ->
            elementCategoryRepository.deleteElementCategory(initialElementCategories.first { it.category == categoryToDelete })
        }

        newCategories.filter { it !in initialCategories }.forEach { categoryToAdd ->
            elementCategoryRepository.insertElementCategory(
                ElementCategory(element, categoryToAdd)
            )
        }
    }

    suspend fun getElementsByCategory(category: Category): List<ElementCategory> {
        return elementCategoryRepository.getElementsByCategory(category)
    }

    suspend fun getCategoriesByElement(element: Element): List<ElementCategory> {
        return elementCategoryRepository.getCategoriesByElement(element)
    }

    suspend fun getElementsWitchAreNotInCategory(category: Category): List<Element> {
        return elementCategoryRepository.getElementsWitchAreNotInCategory(category)
    }

    suspend fun deleteAllElementFromCategory(category: Category) {
        elementCategoryRepository.deleteAllElementFromCategory(category)
    }


}