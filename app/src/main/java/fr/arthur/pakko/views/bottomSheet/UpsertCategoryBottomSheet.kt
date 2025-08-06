package fr.arthur.pakko.views.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.arthur.pakko.R
import fr.arthur.pakko.exceptions.CategoryNameAlreadyUsedException
import fr.arthur.pakko.models.Category
import fr.arthur.pakko.viewmodel.CategoryViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class UpsertCategoryBottomSheet : BottomSheetDialogFragment() {

    private val categoryViewModel: CategoryViewModel by activityViewModel()

    private lateinit var editText: EditText
    private lateinit var pageTitle: TextView
    private lateinit var deleteButton: Button
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryViewModel.error.observe(viewLifecycleOwner) { throwable ->
            throwable?.let {
                val message = when (it) {
                    is CategoryNameAlreadyUsedException -> getString(R.string.category_name_already_used)
                    else -> it.localizedMessage ?: getString(R.string.unknown_error)
                }
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initComponent(view: View) {
        editText = view.findViewById(R.id.entry_category_name)
        deleteButton = view.findViewById(R.id.delete_button)
        pageTitle = view.findViewById(R.id.popup_title)

        if (itemCategory != null) {
            editText.setText(itemCategory!!.nom)
            pageTitle.text = getString(R.string.popup_title_edit_category)
            deleteButton.visibility = View.VISIBLE

            deleteButton.setOnClickListener {
                // supprimer la catégorie
                categoryViewModel.deleteCategories(itemCategory!!)
                dismiss()
            }
        }

        editText.setOnEditorActionListener { _, actionId, _ ->
            if (itemCategory != null) {
                // update
                categoryViewModel.updateCategories(
                    Category(
                        id = itemCategory!!.id,
                        nom = editText.text.toString()
                    )
                )
            } else {
                // insert
                categoryViewModel.insertCategories(
                    Category(
                        nom = editText.text.toString()
                    )
                )
            }

            dismiss()
            true
        }
    }


}