package fr.arthur.pakko.repositories

import fr.arthur.pakko.models.CategorieUi
import fr.arthur.pakko.models.Element
import fr.arthur.pakko.models.ElementUi
import fr.arthur.pakko.room.DAO.ElementDao
import fr.arthur.pakko.utils.toElement
import fr.arthur.pakko.utils.toElementCategorieEntityCrossRef
import fr.arthur.pakko.utils.toElementEntity
import fr.arthur.pakko.utils.toElementUi

class ElementRepository(
    private val elementDao: ElementDao
) {
    suspend fun getAllElements(): List<Element> {
        return elementDao.getAll().map { it.toElement() }
    }

    suspend fun deleteElement(element: Element) {
        // TODO : le supprimer de ses catégories
        elementDao.delete(element.toElementEntity())
    }

    suspend fun getElementsForCategory(categorieId: String): List<ElementUi> {
        return elementDao.getElementsForCategory(categorieId).map { it.toElementUi() }
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