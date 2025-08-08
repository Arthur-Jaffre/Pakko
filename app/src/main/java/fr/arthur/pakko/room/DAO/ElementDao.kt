package fr.arthur.pakko.room.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import fr.arthur.pakko.room.entities.ElementCategorieEntityCrossRef
import fr.arthur.pakko.room.entities.ElementEntity

@Dao
interface ElementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(element: ElementEntity)

    @Delete
    suspend fun delete(element: ElementEntity)

    @Query("SELECT * FROM elements ORDER BY nom")
    suspend fun getAll(): List<ElementEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRefs(crossRefs: List<ElementCategorieEntityCrossRef>)

    @Transaction
    suspend fun insertElementWithCrossRefs(
        element: ElementEntity,
        crossRefs: List<ElementCategorieEntityCrossRef>
    ) {
        insert(element)
        insertCrossRefs(crossRefs)
    }
}