package fr.arthur.pakko.models

data class ElementCategory(
    val element: Element,
    val category: Category,
    val comment: String? = null,
    val coche: Boolean = false
)