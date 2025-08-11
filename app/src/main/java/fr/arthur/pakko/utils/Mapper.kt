package fr.arthur.pakko.utils

import fr.arthur.pakko.models.Category
import fr.arthur.pakko.models.Element
import fr.arthur.pakko.models.ElementCategory
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

fun ElementCategory.toElementCategorieCrossRef(): ElementCategorieEntityCrossRef {
    return ElementCategorieEntityCrossRef(
        element_id = element.id,
        categorie_id = category.id,
        commentaire = comment,
        coche = coche
    )
}

fun ElementCategorieEntityCrossRef.toElementCategory(
    element: Element,
    category: Category
): ElementCategory {
    return ElementCategory(
        element = element,
        category = category,
        comment = commentaire,
        coche = coche
    )
}