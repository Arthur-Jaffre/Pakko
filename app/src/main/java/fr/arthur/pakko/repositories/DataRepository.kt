package fr.arthur.pakko.repositories

import fr.arthur.pakko.models.ExportData
import fr.arthur.pakko.room.DAO.CategorieDao
import fr.arthur.pakko.room.DAO.ElementCategorieDao
import fr.arthur.pakko.room.DAO.ElementDao
import fr.arthur.pakko.utils.JsonConverter
import fr.arthur.pakko.utils.toCategorieEntity
import fr.arthur.pakko.utils.toCategory
import fr.arthur.pakko.utils.toElement
import fr.arthur.pakko.utils.toElementEntity

class DataRepository(
    private val categorieDao: CategorieDao,
    private val elementDao: ElementDao,
    private val elementCategorieDao: ElementCategorieDao
) {

    suspend fun exportData(): String {
        val categories = categorieDao.getAll().map { it.toCategory() }
        val elements = elementDao.getAll().map { it.toElement() }
        val elementsCategories = elementCategorieDao.getAll()

        val exportData = ExportData(categories, elements, elementsCategories)
        return JsonConverter.toJson(exportData)
    }

    suspend fun importData(jsonString: String) {
        val importData = JsonConverter.fromJson(jsonString)

        // Insérer sans supprimer, en évitant les doublons selon l'id
        importData.categories.forEach { category ->
            val existing = categorieDao.getCategorieById(category.id)
            if (existing == null) {
                categorieDao.insert(category.toCategorieEntity())
            } else {
                categorieDao.update(category.toCategorieEntity())
            }
        }

        importData.elements.forEach { element ->
            val existing = elementDao.getElementById(element.id)
            if (existing == null) {
                elementDao.insert(element.toElementEntity())
            } else {
                elementDao.update(element.toElementEntity())
            }
        }

        importData.elementsCategories.forEach { crossRef ->
            val existing = elementCategorieDao.getByElementAndCategorie(
                crossRef.element_id,
                crossRef.categorie_id
            )
            if (existing == null) {
                elementCategorieDao.insert(crossRef)
            } else {
                elementCategorieDao.update(crossRef)
            }
        }
    }
}
