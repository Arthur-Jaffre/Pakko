package fr.arthur.pakko.models

data class CategorieUi(
    val category: Category,
    var isSelected: Boolean = false,
    var comment: String? = null,
    var coche: Boolean = false
)
