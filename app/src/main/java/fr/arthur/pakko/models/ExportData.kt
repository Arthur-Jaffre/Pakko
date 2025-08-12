package fr.arthur.pakko.models

import fr.arthur.pakko.room.entities.ElementCategorieEntityCrossRef

data class ExportData(
    val categories: List<Category>,
    val elements: List<Element>,
    val elementsCategories: List<ElementCategorieEntityCrossRef>
)