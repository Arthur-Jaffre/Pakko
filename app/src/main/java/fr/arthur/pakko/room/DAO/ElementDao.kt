package fr.arthur.pakko.room.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fr.arthur.pakko.room.entities.ElementEntity

@Dao
interface ElementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(element: ElementEntity)

    @Update
    suspend fun update(element: ElementEntity)

    @Delete
    suspend fun delete(element: ElementEntity)

    @Query("SELECT * FROM elements ORDER BY nom ASC")
    suspend fun getAll(): List<ElementEntity>

    @Query("SELECT * FROM elements WHERE id = :id")
    suspend fun getElementById(id: String): ElementEntity?

}