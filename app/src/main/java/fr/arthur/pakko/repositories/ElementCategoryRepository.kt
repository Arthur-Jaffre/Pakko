package fr.arthur.pakko.repositories

import androidx.room.Transaction
import fr.arthur.pakko.models.Category
import fr.arthur.pakko.models.Element
import fr.arthur.pakko.models.ElementCategory
import fr.arthur.pakko.room.DAO.ElementCategorieDao
import fr.arthur.pakko.utils.toCategory
import fr.arthur.pakko.utils.toElement
import fr.arthur.pakko.utils.toElementCategorieCrossRef
import fr.arthur.pakko.utils.toElementCategory

class ElementCategoryRepository(
    private val elementCategorieDao: ElementCategorieDao
) {
    suspend fun insertElementCategory(elementCategorie: ElementCategory) {
        elementCategorieDao.insert(elementCategorie.toElementCategorieCrossRef())
    }

    suspend fun updateElementCategory(elementCategorie: ElementCategory) {
        elementCategorieDao.update(elementCategorie.toElementCategorieCrossRef())
    }

    suspend fun deleteElementCategory(elementCategorie: ElementCategory) {
        elementCategorieDao.delete(elementCategorie.toElementCategorieCrossRef())
    }

    private suspend fun getByElementAndCategorie(
        element: Element,
        category: Category
    ): ElementCategory? {
        return elementCategorieDao.getByElementAndCategorie(element.id, category.id)
            ?.toElementCategory(element, category)
    }

    @Transaction
    suspend fun getElementsByCategory(category: Category): List<ElementCategory> {
        return elementCategorieDao.getElementsByCategory(category.id)
            .map { it.toElement() }.mapNotNull { element ->
                getByElementAndCategorie(element, category)
            }.sortedBy { it.element.nom }
    }

    @Transaction
    suspend fun getCategoriesByElement(element: Element): List<ElementCategory> {
        return elementCategorieDao.getCategoriesByElement(element.id)
            .map { it.toCategory() }.mapNotNull { category ->
                getByElementAndCategorie(element, category)
            }.sortedBy { it.category.nom }
    }

    suspend fun getElementsWitchAreNotInCategory(category: Category): List<Element> {
        return elementCategorieDao.getElementsNotInCategory(category.id)
            .map { it.toElement() }.sortedBy { it.nom }
    }

    suspend fun deleteAllElementFromCategory(category: Category) {
        elementCategorieDao.deleteAllElementFromCategory(category.id)
    }


}