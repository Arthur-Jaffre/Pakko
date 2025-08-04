package fr.arthur.pakko.models

import java.util.UUID

data class Element(
    val id: String = UUID.randomUUID().toString(),
    val nom: String = "",
)