package fr.arthur.pakko.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.arthur.pakko.models.ExportData

object JsonConverter {
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    fun toJson(exportData: ExportData): String {
        return gson.toJson(exportData)
    }

    fun fromJson(jsonString: String): ExportData {
        return gson.fromJson(jsonString, ExportData::class.java)
    }
}
