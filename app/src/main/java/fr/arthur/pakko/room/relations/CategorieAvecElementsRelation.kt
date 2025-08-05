package fr.arthur.pakko.room.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import fr.arthur.pakko.room.entities.CategorieEntity
import fr.arthur.pakko.room.entities.ElementCategorieEntityCrossRef
import fr.arthur.pakko.room.entities.ElementEntity

data class CategorieAvecElementsRelation(
    @Embedded val categorie: CategorieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categorie_id",
        associateBy = Junction(ElementCategorieEntityCrossRef::class)
    )
    val elements: List<ElementEntity>
)
