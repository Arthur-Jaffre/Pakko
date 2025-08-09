package fr.arthur.pakko.room.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import fr.arthur.pakko.room.entities.ElementCategorieEntityCrossRef
import fr.arthur.pakko.room.entities.ElementEntity

@Dao
interface ElementDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(element: ElementEntity)

    @Delete
    suspend fun delete(element: ElementEntity)

    @Query("SELECT * FROM elements ORDER BY nom")
    suspend fun getAll(): List<ElementEntity>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertCrossRefs(crossRefs: List<ElementCategorieEntityCrossRef>)

    @Update
    suspend fun update(element: ElementEntity)

    @Query(
        """
    SELECT e.* 
    FROM elements e
    INNER JOIN elements_categories ec 
        ON ec.element_id = e.id
    WHERE ec.categorie_id = :categoryId
    """
    )
    suspend fun getElementsByCategory(categoryId: String): List<ElementEntity>

    @Query("DELETE FROM elements_categories WHERE element_id = :elementId")
    suspend fun deleteAllElementCategories(elementId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateCrossRef(crossRef: ElementCategorieEntityCrossRef)

    @Transaction
    suspend fun updateElementWithCategories(
        element: ElementEntity,
        selectedCategories: List<ElementCategorieEntityCrossRef>
    ) {
        // 1. Mise à jour de l'élément
        update(element)
        // 2. Suppression des liaisons existantes
        deleteAllElementCategories(element.id)
        // 3. Réinsertion des nouvelles liaisons
        selectedCategories.forEach { categoryUi ->
            insertOrUpdateCrossRef(categoryUi)
        }
    }

}