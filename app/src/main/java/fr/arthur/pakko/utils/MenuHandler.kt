package fr.arthur.pakko.utils

import android.widget.ImageButton
import android.widget.PopupMenu
import fr.arthur.pakko.R

class MenuHandler(
    private val menuButton: ImageButton,
    private val importExportHandler: ImportExportHandler
) {
    fun setupMenu() {
        menuButton.setOnClickListener { view ->
            val popup = PopupMenu(view.context, view)
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
