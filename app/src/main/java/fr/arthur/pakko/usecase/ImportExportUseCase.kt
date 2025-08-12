package fr.arthur.pakko.usecase

import fr.arthur.pakko.repositories.DataRepository

class ImportExportUseCase(private val repository: DataRepository) {

    suspend fun exportToJson(): String = repository.exportData()

    suspend fun importFromJson(json: String) = repository.importData(json)
}
