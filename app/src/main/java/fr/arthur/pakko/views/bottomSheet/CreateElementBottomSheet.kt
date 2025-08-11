package fr.arthur.pakko.views.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.arthur.pakko.R
import fr.arthur.pakko.models.Category
import fr.arthur.pakko.models.Element
import fr.arthur.pakko.viewmodel.ElementViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CreateElementBottomSheet : BottomSheetDialogFragment() {
    private lateinit var entryComment: EditText
    lateinit var currentCategory: Category
    private val elementViewModel: ElementViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.popup_create_element, container, false)

        initComponent(view)

        return view
    }

    private fun initComponent(view: View) {
        entryComment = view.findViewById(R.id.entry_element_comment)
        entryComment.setOnEditorActionListener { _, actionId, _ ->
            // modifier le commentaire de l'élément en fonction de sa catégorie
            elementViewModel.addElementByCategory(
                Element(nom = entryComment.text.toString()),
                currentCategory
            )
            dismiss()
            true
        }
    }
}