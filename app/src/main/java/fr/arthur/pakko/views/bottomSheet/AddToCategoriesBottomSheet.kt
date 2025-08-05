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
import fr.arthur.pakko.models.Category
import fr.arthur.pakko.models.Element

class AddToCategoriesBottomSheet : BottomSheetDialogFragment() {
    lateinit var element: Element
    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var categoriesAdapter: AllCategoriesAdapter

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
        categoriesAdapter = AllCategoriesAdapter(
            mutableListOf(
                Category(nom = "categorie1"),
                Category(nom = "categorie2"),
                Category(nom = "categorie3"),
                Category(nom = "categorie4"),
                Category(nom = "categorie5")
            )
        )
        categoriesRecyclerView.adapter = categoriesAdapter
    }
}