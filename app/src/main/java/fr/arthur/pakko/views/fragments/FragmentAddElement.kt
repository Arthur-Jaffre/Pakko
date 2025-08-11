package fr.arthur.pakko.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.arthur.pakko.R
import fr.arthur.pakko.adapters.AllCategoriesAdapter
import fr.arthur.pakko.models.Category
import fr.arthur.pakko.models.CategoryUi
import fr.arthur.pakko.models.Element
import fr.arthur.pakko.models.ElementCategory
import fr.arthur.pakko.viewmodel.CategoryViewModel
import fr.arthur.pakko.viewmodel.ElementViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentAddElement : Fragment() {
    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var editText: EditText
    private lateinit var buttonDelete: Button
    private lateinit var buttonSave: Button
    private lateinit var adapter: AllCategoriesAdapter
    private var currentElement: Element? = null
    private var currentElementCategory: List<ElementCategory> = emptyList()
    private val categoryViewModel: CategoryViewModel by viewModel()
    private val elementViewModel: ElementViewModel by viewModel() // appelé aussi dans fragment parent mais pas d'UI à modif là-bas

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
        buttonDelete.setOnClickListener { onDelete() }

        buttonSave = rootView.findViewById(R.id.button_save)
        buttonSave.setOnClickListener { onSaveClicked() }
    }

    private fun setupRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recycler_view_all_category)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = AllCategoriesAdapter()
        recyclerView.adapter = adapter
    }

    private fun observeViewModels() {
        var allCategoriesLoaded: List<Category>? = null

        categoryViewModel.allCategories.observe(viewLifecycleOwner) { allCategories ->
            allCategoriesLoaded = allCategories
            updateAdapterIfReady(allCategoriesLoaded, currentElementCategory)
        }

        if (currentElement != null) {
            categoryViewModel.categoriesByElement.observe(viewLifecycleOwner) { categoriesByElement ->
                currentElementCategory = categoriesByElement
                updateAdapterIfReady(allCategoriesLoaded, currentElementCategory)
            }
            categoryViewModel.getCategoriesByElement(currentElement!!)
        }

        categoryViewModel.getAllCategories()
    }

    private fun updateAdapterIfReady(
        allCategories: List<Category>?,
        categoriesByElement: List<ElementCategory>?
    ) {
        if (allCategories != null && (currentElement == null || categoriesByElement != null)) {
            val currentElementCategory = categoriesByElement ?: emptyList()
            adapter.submitList(
                allCategories.map { category ->
                    CategoryUi(
                        category,
                        coche = currentElementCategory.any { it.category.id == category.id }
                    )
                }
            )
        }
    }

    private fun onDelete() {
        currentElement?.let {
            lifecycleScope.launch {
                elementViewModel.deleteElementWithCategories(it, currentElementCategory)
                findNavController().navigateUp()
            }
            Toast.makeText(context, getString(R.string.element_deleted), Toast.LENGTH_SHORT).show()
        }
    }

    private fun onSaveClicked() {
        val elementName = editText.text.toString().trim()

        if (currentElement == null) {
            elementViewModel.addElementWithCategories(
                Element(nom = elementName),
                adapter.getSelectedCategories()
            )
            // réinitialiser le fragment
            initFragment()
        } else {
            // destruction du fragment donc obliger d'attendre la fin de la transaction + suspend (VM)
            lifecycleScope.launch {
                elementViewModel.updateElementWithCategories(
                    currentElement!!.copy(nom = elementName),
                    adapter.getSelectedCategories(),
                    currentElementCategory
                )
                findNavController().navigateUp()
            }
        }

        Toast.makeText(context, getString(R.string.element_saved), Toast.LENGTH_SHORT).show()

    }

    private fun initFragment() {
        editText.text.clear()
        adapter.clearSelectedCategories()
    }
}
