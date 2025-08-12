package fr.arthur.pakko.views.navigation

import android.view.ContextThemeWrapper
import android.widget.ImageButton
import android.widget.PopupMenu
import fr.arthur.pakko.R
import fr.arthur.pakko.utils.ImportExportHandler

class MenuImportHandler(
    private val menuButton: ImageButton,
    private val importExportHandler: ImportExportHandler
) {
    fun setupMenu() {
        menuButton.setOnClickListener { view ->
            val wrapper = ContextThemeWrapper(view.context, R.style.CustomImportMenu)
            val popup = PopupMenu(wrapper, view)
            popup.menuInflater.inflate(R.menu.file_menu, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_import -> {
                        importExportHandler.openJsonFilePicker()
                        true
                    }

                    R.id.action_export -> {
                        importExportHandler.createJsonFile()
                        true
                    }

                    else -> false
                }
            }
            popup.show()
        }
    }

}