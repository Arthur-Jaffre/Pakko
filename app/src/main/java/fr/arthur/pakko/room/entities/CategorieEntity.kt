package fr.arthur.pakko.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategorieEntity(
    @PrimaryKey val id: String,
    val nom: String
)