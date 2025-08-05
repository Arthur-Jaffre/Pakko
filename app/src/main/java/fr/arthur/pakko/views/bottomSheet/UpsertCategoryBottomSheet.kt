package fr.arthur.pakko.views.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.arthur.pakko.R
import fr.arthur.pakko.models.Category

class UpsertCategoryBottomSheet : BottomSheetDialogFragment() {
    private lateinit var editText: EditText
    private lateinit var pageTitle: TextView
    private lateinit var deleteButton: Button
    private lateinit var popupTitle: TextView
    var itemCategory: Category? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.popup_upsert_category, container, false)

        initComponent(view)

        return view
    }

    private fun initComponent(view: View) {
        editText = view.findViewById(R.id.entry_category_name)
        deleteButton = view.findViewById(R.id.delete_button)
        pageTitle = view.findViewById(R.id.popup_title)

        if (itemCategory != null) {
            editText.setText(itemCategory!!.nom)
            pageTitle.text = getString(R.string.popup_title_edit_category)
            deleteButton.visibility = View.VISIBLE
        }
        
        deleteButton.setOnClickListener {
            // TODO : supprimer catégorie
            dismiss()
        }

        editText.setOnEditorActionListener { _, actionId, _ ->
            // val contenu = editText.text.toString()
            // TODO : enregistrer la catégorie
            dismiss()
            true
        }
    }


}