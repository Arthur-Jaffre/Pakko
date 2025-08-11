package fr.arthur.pakko.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.arthur.pakko.R
import fr.arthur.pakko.adapters.CategoriesAdapter
import fr.arthur.pakko.models.Category
import fr.arthur.pakko.viewmodel.CategoryViewModel
import fr.arthur.pakko.views.bottomSheet.UpsertCategoryBottomSheet
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FragmentAllCategories : Fragment() {
    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoriesAdapter
    private lateinit var addCategoryButton: ImageButton
    private val categoryViewModel: CategoryViewModel by activityViewModel() // Utilisation dans popup modification

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_categories, container, false)

        setupComponents(rootView)
        setupRecyclerView()

        return rootView
    }

    private fun setupComponents(rootView: View) {
        addCategoryButton = rootView.findViewById(R.id.add_category_button)
        addCategoryButton.setOnClickListener {
            val bottomSheet = UpsertCategoryBottomSheet()
            bottomSheet.show(parentFragmentManager, "UpsertCategory")
        }
    }

    private fun setupRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recycler_view_category)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = CategoriesAdapter(
            onCategoryClick = { category ->
                openElementsByCategoryFragment(category)
            },
            onCategoryModifyClick = { category ->
                openUpsertCategory(category)
            }
        )

        categoryViewModel.allCategories.observe(viewLifecycleOwner) {
            adapter.submitList(it.toList())
        }
        categoryViewModel.getAllCategories()

        recyclerView.adapter = adapter
    }

    private fun openElementsByCategoryFragment(category: Category) {
        findNavController().navigate(
            R.id.ElementByCategoryFragment,
            Bundle().apply { putSerializable("category", category) }
        )
    }

    private fun openUpsertCategory(category: Category) {
        val bottomSheet = UpsertCategoryBottomSheet()
        bottomSheet.itemCategory = category
        bottomSheet.show(parentFragmentManager, "UpsertCategory")
    }

}