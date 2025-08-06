package fr.arthur.pakko.utils

import fr.arthur.pakko.models.Category
import fr.arthur.pakko.room.entities.CategorieEntity

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