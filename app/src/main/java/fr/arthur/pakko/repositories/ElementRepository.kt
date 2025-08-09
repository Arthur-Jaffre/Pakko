package fr.arthur.pakko.repositories

import fr.arthur.pakko.models.CategorieUi
import fr.arthur.pakko.models.Category
import fr.arthur.pakko.models.Element
import fr.arthur.pakko.room.DAO.ElementDao
import fr.arthur.pakko.utils.toElement
import fr.arthur.pakko.utils.toElementCategorieEntityCrossRef
import fr.arthur.pakko.utils.toElementEntity

class ElementRepository(
    private val elementDao: ElementDao
) {
    suspend fun getAllElements(): List<Element> {
        return elementDao.getAll().map { it.toElement() }
    }

    suspend fun deleteElement(element: Element) {
        elementDao.delete(element.toElementEntity())
    }

    suspend fun getElementsByCategory(category: Category): List<Element> {
        return elementDao.getElementsByCategory(category.id).map { it.toElement() }
    }

    suspend fun updateElementWithCategories(
        element: Element,
        selectedCategories: List<CategorieUi>
    ) {
        elementDao.updateElementWithCategories(
            element.toElementEntity(),
            selectedCategories.map { it.toElementCategorieEntityCrossRef(element.id) }
        )
    }
}