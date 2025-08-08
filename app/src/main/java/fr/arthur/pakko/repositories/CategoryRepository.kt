package fr.arthur.pakko.repositories

import fr.arthur.pakko.models.Category
import fr.arthur.pakko.room.DAO.CategorieDao
import fr.arthur.pakko.room.entities.ElementCategorieEntityCrossRef
import fr.arthur.pakko.utils.toCategorieEntity
import fr.arthur.pakko.utils.toCategory

class CategoryRepository(
    private val categorieDao: CategorieDao
) {
    suspend fun getAllCategories(): List<Category> {
        return categorieDao.getAll().map { it.toCategory() }
    }

    suspend fun insertCategory(category: Category) {
        categorieDao.insert(category.toCategorieEntity())
    }

    suspend fun updateCategory(category: Category) {
        categorieDao.update(category.toCategorieEntity())
    }

    suspend fun deleteCategory(category: Category) {
        categorieDao.delete(category.toCategorieEntity())
    }

    suspend fun getCategoriesForElement(elementId: String): List<Category> {
        return categorieDao.getCategoriesForElement(elementId).map { it.toCategory() }
    }

    suspend fun insertOrUpdateCrossRef(crossRef: ElementCategorieEntityCrossRef) {
        categorieDao.insertOrUpdateElementCategoryCrossRef(crossRef)
    }

    suspend fun deleteElementCategoryCrossRef(elementId: String, categoryId: String) {
        categorieDao.deleteElementCategoryCrossRef(elementId, categoryId)
    }
}
