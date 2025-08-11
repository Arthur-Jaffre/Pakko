package fr.arthur.pakko.repositories

import fr.arthur.pakko.models.Element
import fr.arthur.pakko.room.DAO.ElementDao
import fr.arthur.pakko.utils.toElement
import fr.arthur.pakko.utils.toElementEntity

class ElementRepository(
    private val elementDao: ElementDao
) {
    suspend fun insertElement(element: Element) {
        elementDao.insert(element.toElementEntity())
    }

    suspend fun updateElement(element: Element) {
        elementDao.update(element.toElementEntity())
    }

    suspend fun deleteElement(element: Element) {
        elementDao.delete(element.toElementEntity())
    }

    suspend fun getAllElements(): List<Element> {
        return elementDao.getAll().map { it.toElement() }
    }
}