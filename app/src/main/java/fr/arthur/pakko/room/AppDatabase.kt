package fr.arthur.pakko.room

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.arthur.pakko.room.DAO.CategorieDao
import fr.arthur.pakko.room.DAO.ElementDao
import fr.arthur.pakko.room.entities.CategorieEntity
import fr.arthur.pakko.room.entities.ElementCategorieEntityCrossRef
import fr.arthur.pakko.room.entities.ElementEntity

@Database(
    entities = [CategorieEntity::class, ElementEntity::class, ElementCategorieEntityCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categorieDao(): CategorieDao
    abstract fun elementDao(): ElementDao
}
