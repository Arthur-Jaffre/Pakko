package fr.arthur.pakko.utils

fun formatText(input: String): String {
    return input.trim()
        .split(" ")
        .mapIndexed { index, word ->
            if (word.length > 2 || index == 0) word.replaceFirstChar { it.uppercase() } else word.lowercase()
        }
        .joinToString(" ")
}
