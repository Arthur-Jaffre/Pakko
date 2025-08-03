package fr.arthur.pakko.room.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fr.arthur.pakko.room.entities.CategorieEntity
import fr.arthur.pakko.room.entities.ElementCategorieEntityCrossRef
import fr.arthur.pakko.room.relations.ElementAvecInfosParCategorieRelation

@Dao
interface ElementCategorieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRef(crossRef: ElementCategorieEntityCrossRef)

    @Update
    suspend fun updateCrossRef(crossRef: ElementCategorieEntityCrossRef)

    @Delete
    suspend fun deleteCrossRef(crossRef: ElementCategorieEntityCrossRef)

    @Query(
        """
        SELECT e.*, ec.commentaire, ec.coche
        FROM elements e
        INNER JOIN elements_categories ec ON e.id = ec.element_id
        WHERE ec.categorie_id = :categorieId
        ORDER BY e.nom
    """
    )
    suspend fun getElementsParCategorie(categorieId: Int): List<ElementAvecInfosParCategorieRelation>

    @Query(
        """
        SELECT c.*
        FROM categories c
        INNER JOIN elements_categories ec ON c.id = ec.categorie_id
        WHERE ec.element_id = :elementId
        ORDER BY c.nom
    """
    )
    suspend fun getCategoriesParElement(elementId: Int): List<CategorieEntity>

    @Query(
        """
        UPDATE elements_categories
        SET coche = :coche
        WHERE element_id = :elementId AND categorie_id = :categorieId
    """
    )
    suspend fun setCoche(elementId: Int, categorieId: Int, coche: Boolean)

    @Query(
        """
        UPDATE elements_categories
        SET commentaire = :commentaire
        WHERE element_id = :elementId AND categorie_id = :categorieId
    """
    )
    suspend fun setCommentaire(elementId: Int, categorieId: Int, commentaire: String?)
}