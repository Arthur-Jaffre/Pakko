package fr.arthur.pakko.usecase

import androidx.room.Transaction
import fr.arthur.pakko.exceptions.CategoryNameAlreadyUsedException
import fr.arthur.pakko.models.CategorieUi
import fr.arthur.pakko.models.Category
import fr.arthur.pakko.repositories.CategoryRepository
import fr.arthur.pakko.room.entities.ElementCategorieEntityCrossRef

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

    suspend fun getCategoriesForElement(elementId: String): List<Category> {
        return categoryRepository.getCategoriesForElement(elementId)
    }

    suspend fun updateElementCategory(categoryUI: CategorieUi, elementId: String) {
        if (categoryUI.coche) {
            // Ajout ou mise à jour
            categoryRepository.insertOrUpdateCrossRef(
                ElementCategorieEntityCrossRef(
                    element_id = elementId,
                    categorie_id = categoryUI.category.id,
                    commentaire = categoryUI.comment,
                    coche = true
                )
            )
        } else {
            // Suppression
            categoryRepository.deleteElementCategoryCrossRef(elementId, categoryUI.category.id)
        }
    }


}