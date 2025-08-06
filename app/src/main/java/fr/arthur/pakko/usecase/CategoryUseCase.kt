package fr.arthur.pakko.usecase

import androidx.room.Transaction
import fr.arthur.pakko.exceptions.CategoryNameAlreadyUsedException
import fr.arthur.pakko.models.Category
import fr.arthur.pakko.repositories.CategoryRepository

class CategoryUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend fun getAllCategories(): List<Category> {
        return categoryRepository.getAllCategories()
    }

    suspend fun insertCategory(category: Category) {
        val allCategories = getAllCategories()
        if (allCategories.any { it.nom == category.nom }) {
            throw CategoryNameAlreadyUsedException()
        }
        categoryRepository.insertCategory(category)
    }

    @Transaction
    suspend fun updateCategory(category: Category) {
        val allCategories = getAllCategories()

        // Vérifier que le nouveau nom n'est pas déjà pris par une autre catégorie
        if (allCategories.any { it.nom == category.nom && it.id != category.id }) {
            throw CategoryNameAlreadyUsedException()
        }

        // Récupérer la catégorie actuelle
        val current = allCategories.find { it.id == category.id }

        // Ne mettre à jour que si le nom a changé
        if (current!!.nom != category.nom) {
            categoryRepository.updateCategory(category)
        }
    }


    suspend fun deleteCategory(category: Category) {
        // TODO : supprimer les éléments de la catégorie
        categoryRepository.deleteCategory(category)
    }

}