package fr.arthur.pakko.room

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.arthur.pakko.room.DAO.CategorieDao
import fr.arthur.pakko.room.DAO.ElementCategorieDao
import fr.arthur.pakko.room.DAO.ElementDao
import fr.arthur.pakko.room.entities.CategorieEntity
import fr.arthur.pakko.room.entities.ElementCategorieEntityCrossRef

@Database(
    entities = [CategorieEntity::class, CategorieEntity::class, ElementCategorieEntityCrossRef::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categorieDao(): CategorieDao
    abstract fun elementDao(): ElementDao
    abstract fun elementCategorieDao(): ElementCategorieDao
}
