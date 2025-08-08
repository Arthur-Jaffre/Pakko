package fr.arthur.pakko.repositories

import fr.arthur.pakko.models.Element
import fr.arthur.pakko.room.DAO.ElementDao
import fr.arthur.pakko.room.entities.ElementCategorieEntityCrossRef
import fr.arthur.pakko.utils.toElement
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

    suspend fun insertElementWithCrossRefs(
        element: Element,
        crossRefs: List<ElementCategorieEntityCrossRef>
    ) {
        elementDao.insertElementWithCrossRefs(element.toElementEntity(), crossRefs)
    }
}