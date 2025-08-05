package fr.arthur.pakko.models

import java.io.Serializable
import java.util.UUID

data class Category(
    val id: String = UUID.randomUUID().toString(),
    val nom: String = "",
) : Serializable