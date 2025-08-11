package fr.arthur.pakko.views.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.arthur.pakko.R
import fr.arthur.pakko.adapters.ElementsSelectAdapter
import fr.arthur.pakko.models.Category
import fr.arthur.pakko.models.Element
import fr.arthur.pakko.viewmodel.ElementViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CreateElementBottomSheet : BottomSheetDialogFragment() {
    private lateinit var entryComment: EditText
    lateinit var currentCategory: Category
    private lateinit var elementsSelectAdapter: ElementsSelectAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var modifyElementTitle: TextView
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

    private fun onElementClicked(element: Element) {
        elementViewModel.addElementByCategory(element, currentCategory)
        Toast.makeText(context, getString(R.string.element_saved), Toast.LENGTH_SHORT).show()
        dismiss()
    }

    private fun initComponent(view: View) {
        modifyElementTitle = view.findViewById(R.id.existing_element_title)
        recyclerView = view.findViewById(R.id.recycler_existing_elements)
        recyclerView.layoutManager = LinearLayoutManager(context)
        elementsSelectAdapter = ElementsSelectAdapter(
            onElementClicked = { element -> onElementClicked(element) }
        )
        recyclerView.adapter = elementsSelectAdapter

        elementViewModel.elementsNotInCategory.observe(viewLifecycleOwner) { elements ->
            elementsSelectAdapter.submitList(elements)
            if (elements.isEmpty()) {
                modifyElementTitle.visibility = View.GONE
                recyclerView.visibility = View.GONE
            } else {
                modifyElementTitle.visibility = View.VISIBLE
                recyclerView.visibility = View.VISIBLE
            }
        }
        elementViewModel.getElementsNotInCategory(currentCategory)


        entryComment = view.findViewById(R.id.entry_element_name)
        entryComment.setOnEditorActionListener { _, actionId, _ ->
            // modifier le commentaire de l'élément en fonction de sa catégorie
            elementViewModel.addElementByCategory(
                Element(nom = entryComment.text.toString().trim()),
                currentCategory
            )
            dismiss()
            true
        }
    }
}