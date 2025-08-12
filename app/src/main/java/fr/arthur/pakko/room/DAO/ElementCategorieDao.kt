package fr.arthur.pakko.room.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fr.arthur.pakko.room.entities.CategorieEntity
import fr.arthur.pakko.room.entities.ElementCategorieEntityCrossRef
import fr.arthur.pakko.room.entities.ElementEntity

@Dao
interface ElementCategorieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(elementCategorie: ElementCategorieEntityCrossRef)

    @Update
    suspend fun update(elementCategorie: ElementCategorieEntityCrossRef)

    @Delete
    suspend fun delete(elementCategorie: ElementCategorieEntityCrossRef)

    @Query("SELECT * FROM elements_categories")
    suspend fun getAll(): List<ElementCategorieEntityCrossRef>

    @Query("SELECT * FROM elements_categories WHERE element_id = :elementId AND categorie_id = :categorieId")
    suspend fun getByElementAndCategorie(
        elementId: String,
        categorieId: String
    ): ElementCategorieEntityCrossRef?

    @Query("SELECT e.* FROM elements e JOIN elements_categories ec ON e.id = ec.element_id WHERE ec.categorie_id = :categorieId")
    suspend fun getElementsByCategory(categorieId: String): List<ElementEntity>

    @Query("SELECT c.* FROM categories c JOIN elements_categories ec ON c.id = ec.categorie_id WHERE ec.element_id = :elementId")
    suspend fun getCategoriesByElement(elementId: String): List<CategorieEntity>

    @Query("DELETE FROM elements_categories WHERE categorie_id = :categorieId")
    suspend fun deleteAllElementFromCategory(categorieId: String)

    @Query("SELECT e.* FROM elements e LEFT JOIN elements_categories ec ON e.id = ec.element_id AND ec.categorie_id = :categorieId WHERE ec.element_id IS NULL")
    suspend fun getElementsNotInCategory(categorieId: String): List<ElementEntity>

}