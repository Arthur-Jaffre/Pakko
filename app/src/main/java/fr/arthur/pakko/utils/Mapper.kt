package fr.arthur.pakko.utils

import fr.arthur.pakko.models.CategorieUi
import fr.arthur.pakko.models.Category
import fr.arthur.pakko.models.Element
import fr.arthur.pakko.room.entities.CategorieEntity
import fr.arthur.pakko.room.entities.ElementCategorieEntityCrossRef
import fr.arthur.pakko.room.entities.ElementEntity

fun CategorieEntity.toCategory(): Category {
    return Category(
        id = id,
        nom = nom
    )
}

fun Category.toCategorieEntity(): CategorieEntity {
    return CategorieEntity(
        id = id,
        nom = nom
    )
}

fun ElementEntity.toElement(): Element {
    return Element(
        id = id,
        nom = nom
    )
}

fun Element.toElementEntity(): ElementEntity {
    return ElementEntity(
        id = id,
        nom = nom
    )
}

fun CategorieUi.toElementCategorieEntityCrossRef(elementId: String): ElementCategorieEntityCrossRef {
    return ElementCategorieEntityCrossRef(
        element_id = elementId,
        categorie_id = category.id,
        commentaire = comment,
        coche = coche
    )
}


fun Category.toCategorieUi(isSelected: Boolean = false): CategorieUi {
    return CategorieUi(
        category = this,
        isSelected = isSelected
    )
}
