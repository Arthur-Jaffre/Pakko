package fr.arthur.pakko.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "elements")
data class ElementEntity(
    @PrimaryKey val id: Int,
    val nom: String
)