package fr.arthur.pakko.models

import java.io.Serializable

// Correspond à un élement pour une catégorie
data class ElementCategory(
    val element: Element,
    val category: Category,
    val comment: String? = null,
    val coche: Boolean = false
) : Serializable

// Permet l'affichage dans les adapters
data class CategoryUi(
    val category: Category,
    val comment: String? = null,
    var coche: Boolean = false
)