package fr.arthur.pakko.views.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.arthur.pakko.R
import fr.arthur.pakko.models.Category
import fr.arthur.pakko.models.ElementCategory
import fr.arthur.pakko.utils.formatText
import fr.arthur.pakko.viewmodel.ElementViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ModifyElementBottomSheet : BottomSheetDialogFragment() {
    private lateinit var deleteButton: LinearLayout
    private lateinit var editButton: LinearLayout
    private lateinit var title: TextView
    private lateinit var entryComment: EditText
    lateinit var elementCategory: ElementCategory
    lateinit var category: Category
    private val elementViewModel: ElementViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.popup_modify_element, container, false)

        initComponent(view)

        return view
    }

    private fun initComponent(view: View) {
        deleteButton = view.findViewById(R.id.button_remove_element_from_category)
        editButton = view.findViewById(R.id.button_edit_element)
        title = view.findViewById(R.id.element_title)
        title.text = elementCategory.element.nom
        entryComment = view.findViewById(R.id.entry_element_comment)
        entryComment.setText(elementCategory.comment)

        deleteButton.setOnClickListener {
            // supprimer l'élément de la catégorie
            elementViewModel.deleteElementFromCategory(elementCategory)
            Toast.makeText(
                context,
                getString(R.string.element_removed_from_category),
                Toast.LENGTH_SHORT
            ).show()
            dismiss()
        }

        editButton.setOnClickListener {
            findNavController().navigate(
                R.id.addElementFragment,
                Bundle().apply { putSerializable("element", elementCategory.element) }
            )

//            (activity as? MainActivity)?.highlightMenuItem(R.id.addElementFragment)
            dismiss()
        }

        entryComment.setOnEditorActionListener { _, actionId, _ ->
            // modifier le commentaire de l'élément en fonction de sa catégorie
            elementViewModel.updateElementCategory(
                elementCategory.copy(
                    comment = formatText(
                        entryComment.text.toString()
                    )
                )
            )
            dismiss()
            true
        }
    }
}