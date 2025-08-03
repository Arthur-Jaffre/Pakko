package fr.arthur.pakko.room.relations

import androidx.room.Embedded
import fr.arthur.pakko.room.entities.ElementEntity

data class ElementAvecInfosParCategorieRelation(
    @Embedded val element: ElementEntity,
    val commentaire: String?,
    val coche: Boolean
)
