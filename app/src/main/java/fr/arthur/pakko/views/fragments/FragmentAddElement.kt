package fr.arthur.pakko.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.arthur.pakko.R
import fr.arthur.pakko.adapters.AllCategoriesAdapter
import fr.arthur.pakko.models.CategorieUi
import fr.arthur.pakko.models.Category
import fr.arthur.pakko.models.Element
import fr.arthur.pakko.viewmodel.CategoryViewModel
import fr.arthur.pakko.viewmodel.ElementViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentAddElement : Fragment() {
    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var editText: EditText
    private lateinit var buttonDelete: Button
    private lateinit var buttonSave: Button
    private lateinit var adapter: AllCategoriesAdapter
    private var currentElement: Element? = null
    private val categoryViewModel: CategoryViewModel by viewModel()
    private val elementViewModel: ElementViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_add_element, container, false)
        initData()
        initViews()
        setupRecyclerView()
        observeViewModels()
        return rootView
    }

    private fun initData() {
        currentElement = arguments?.getSerializable("element") as? Element
    }

    private fun initViews() {
        editText = rootView.findViewById(R.id.edit_element_input)
        editText.setText(currentElement?.nom.orEmpty())

        buttonDelete = rootView.findViewById(R.id.button_delete)
        buttonDelete.visibility = if (currentElement != null) View.VISIBLE else View.GONE
        buttonDelete.setOnClickListener { onDeleteClicked() }

        buttonSave = rootView.findViewById(R.id.button_save)
        buttonSave.setOnClickListener { onSaveClicked() }
    }

    private fun setupRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recycler_view_all_category)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = AllCategoriesAdapter()
        recyclerView.adapter = adapter

        categoryViewModel.getAllCategories()
        currentElement?.let { categoryViewModel.getCategoriesForElement(it.id) }
    }

    private fun observeViewModels() {
        categoryViewModel.categories.observe(viewLifecycleOwner) { allCategories ->
            if (currentElement == null) {
                // Cas ajout : coche = false partout
                adapter.submitList(allCategories.map { CategorieUi(it, coche = false) })
            } else {
                updateUiIfReady(allCategories, categoryViewModel.categoriesForElement.value)
            }
        }

        if (currentElement != null) {
            categoryViewModel.categoriesForElement.observe(viewLifecycleOwner) { associatedCategories ->
                updateUiIfReady(categoryViewModel.categories.value, associatedCategories)
            }
            categoryViewModel.getCategoriesForElement(currentElement!!.id)
        }
        categoryViewModel.getAllCategories()
    }

    private fun onDeleteClicked() {
        currentElement?.let {
            elementViewModel.deleteElement(it)
            findNavController().navigateUp()
        }
    }

    private fun onSaveClicked() {
        val newName = editText.text.toString()

        elementViewModel.updateElementWithCategories(
            currentElement?.copy(nom = newName) ?: Element(nom = newName),
            adapter.getSelectedCategories()
        )

        Toast.makeText(context, getString(R.string.element_saved), Toast.LENGTH_SHORT).show()
        if (currentElement != null) {
            findNavController().navigateUp()
        }
    }


    private fun updateUiIfReady(allCategories: List<Category>?, associated: List<Category>?) {
        if (allCategories == null || associated == null) return
        adapter.submitList(
            allCategories.map { category ->
                CategorieUi(category, coche = associated.any { it.id == category.id })
            }
        )
    }
}
