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

    @Query("SELECT * FROM categories ORDER BY nom ASC")
    suspend fun getAll(): List<CategorieEntity>
}