package fr.arthur.pakko.usecase

import fr.arthur.pakko.models.ElementCategory
import fr.arthur.pakko.repositories.CategoryRepository
import fr.arthur.pakko.repositories.ElementRepository

class ElementCategoryUseCase(
    private val elementRepository: ElementRepository,
    private val categoryRepository: CategoryRepository
) {
    suspend fun updateElementCategory(elementCategory: ElementCategory) {
    }
}