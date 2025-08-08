package fr.arthur.pakko.views.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.arthur.pakko.R
import fr.arthur.pakko.adapters.AllCategoriesAdapter
import fr.arthur.pakko.models.CategorieUi
import fr.arthur.pakko.models.Category
import fr.arthur.pakko.models.Element
import fr.arthur.pakko.viewmodel.CategoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddToCategoriesBottomSheet : BottomSheetDialogFragment() {
    lateinit var element: Element
    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var categoriesAdapter: AllCategoriesAdapter
    private val categoryViewModel: CategoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.popup_add_element_to_categories, container, false)

        initComponent(view)

        return view
    }

    private fun initComponent(view: View) {
        categoriesRecyclerView = view.findViewById(R.id.categories_recycler_view)
        categoriesRecyclerView.layoutManager = LinearLayoutManager(context)
        categoriesAdapter = AllCategoriesAdapter()
        categoriesRecyclerView.adapter = categoriesAdapter

        // Observer les deux flux en parallèle
        categoryViewModel.categories.observe(viewLifecycleOwner) { allCategories ->
            updateUiIfReady(allCategories, categoryViewModel.categoriesForElement.value)
        }

        categoryViewModel.categoriesForElement.observe(viewLifecycleOwner) { associatedCategories ->
            updateUiIfReady(categoryViewModel.categories.value, associatedCategories)
        }

        // Lancer les chargements
        categoryViewModel.getCategoriesForElement(element.id)
        categoryViewModel.getAllCategories()
    }

    /**
     * Met à jour l'adapter des catégories avec l'état de sélection pour l'élément.
     *
     * Cette méthode est appelée lorsque l'une des deux LiveData — la liste complète des catégories
     * ou la liste des catégories associées à un élément — est mise à jour.
     * Elle attend que les deux soient non nulles pour synchroniser les cases cochées.
     * Ne fait rien tant que l'une des deux listes est encore null (chargement en cours).
     *
     * @param allCategories La liste complète des catégories disponibles dans l'application.
     * @param associated La liste des catégories dans lesquelles l'élément courant est déjà présent.
     *
     */
    private fun updateUiIfReady(
        allCategories: List<Category>?,
        associated: List<Category>?
    ) {
        if (allCategories == null || associated == null) return

        val updatedUi = allCategories.map { category ->
            CategorieUi(category, isSelected = associated.any { it.id == category.id })
        }
        categoriesAdapter.submitList(updatedUi)
    }
}