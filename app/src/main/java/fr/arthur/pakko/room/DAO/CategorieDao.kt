package fr.arthur.pakko.room.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fr.arthur.pakko.room.entities.CategorieEntity

@Dao
interface CategorieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categorie: CategorieEntity)

    @Update
    suspend fun update(categorie: CategorieEntity)

    @Delete
    suspend fun delete(categorie: CategorieEntity)

    @Query("SELECT * FROM categories ORDER BY nom")
    suspend fun getAll(): List<CategorieEntity>

    @Query(
        """
    SELECT c.* FROM categories c
    INNER JOIN elements_categories ec ON ec.categorie_id = c.id
    WHERE ec.element_id = :elementId
    """
    )
    suspend fun getCategoriesForElement(elementId: String): List<CategorieEntity>
}
