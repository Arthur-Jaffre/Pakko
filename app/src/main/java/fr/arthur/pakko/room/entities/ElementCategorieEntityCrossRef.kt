package fr.arthur.pakko.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "elements_categories",
    primaryKeys = ["element_id", "categorie_id"],
    foreignKeys = [
        ForeignKey(
            entity = ElementEntity::class,
            parentColumns = ["id"],
            childColumns = ["element_id"],
            onDelete = ForeignKey.Companion.CASCADE
        ),
        ForeignKey(
            entity = CategorieEntity::class,
            parentColumns = ["id"],
            childColumns = ["categorie_id"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ]
)
data class ElementCategorieEntityCrossRef(
    val element_id: Int,
    val categorie_id: Int,
    val commentaire: String?,
    val coche: Boolean
)