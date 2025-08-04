package fr.arthur.pakko.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "elements_categories",
    primaryKeys = ["element_id", "categorie_id"],
    foreignKeys = [
        ForeignKey(
            entity = ElementEntity::class,
            parentColumns = ["id"],
            childColumns = ["element_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CategorieEntity::class,
            parentColumns = ["id"],
            childColumns = ["categorie_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["element_id"]),
        Index(value = ["categorie_id"])
    ]
)
data class ElementCategorieEntityCrossRef(
    val element_id: String,
    val categorie_id: String,
    val commentaire: String?,
    val coche: Boolean
)
