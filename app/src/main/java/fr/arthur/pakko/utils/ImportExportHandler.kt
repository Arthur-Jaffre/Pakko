package fr.arthur.pakko.utils

import android.net.Uri
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import fr.arthur.pakko.R
import fr.arthur.pakko.usecase.ImportExportUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ImportExportHandler(
    private val activity: AppCompatActivity,
    private val useCase: ImportExportUseCase
) {

    private val importJsonLauncher =
        activity.registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                activity.lifecycleScope.launch {
                    val jsonString = readTextFromUri(it)
                    useCase.importFromJson(jsonString)
                    Toast.makeText(
                        activity,
                        activity.getString(R.string.import_ok),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    private val exportJsonLauncher =
        activity.registerForActivityResult(ActivityResultContracts.CreateDocument("application/json")) { uri: Uri? ->
            uri?.let {
                activity.lifecycleScope.launch {
                    val jsonString = useCase.exportToJson()
                    writeTextToUri(it, jsonString)
                    Toast.makeText(
                        activity,
                        activity.getString(R.string.export_ok),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    fun openJsonFilePicker() {
        importJsonLauncher.launch(arrayOf("application/json"))
    }

    fun createJsonFile() {
        exportJsonLauncher.launch(
            "export_${
                SimpleDateFormat(
                    "yyyyMMdd_HHmmss_SSS",
                    Locale.getDefault()
                ).format(Date())
            }.json"
        )
    }

    private suspend fun readTextFromUri(uri: Uri): String = withContext(Dispatchers.IO) {
        activity.contentResolver.openInputStream(uri)?.bufferedReader().use { it?.readText() } ?: ""
    }

    private suspend fun writeTextToUri(uri: Uri, text: String) = withContext(Dispatchers.IO) {
        activity.contentResolver.openOutputStream(uri)?.bufferedWriter().use { it?.write(text) }
    }
}
