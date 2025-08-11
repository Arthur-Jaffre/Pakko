package fr.arthur.pakko.usecase

import fr.arthur.pakko.models.Category
import fr.arthur.pakko.repositories.CategoryRepository

class CategoryUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend fun insertCategory(category: Category) {
        categoryRepository.insertCategory(category)
    }

    suspend fun updateCategory(category: Category) {
        categoryRepository.updateCategory(category)
    }

    suspend fun deleteCategory(category: Category) {
        categoryRepository.deleteCategory(category)
    }

    suspend fun getAllCategories(): List<Category> {
        return categoryRepository.getAllCategories()
    }
}