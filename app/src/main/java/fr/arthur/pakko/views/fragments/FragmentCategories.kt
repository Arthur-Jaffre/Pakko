package fr.arthur.pakko.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.arthur.pakko.R
import fr.arthur.pakko.adapters.CategoriesAdapter
import fr.arthur.pakko.models.Category

class FragmentCategories : Fragment() {
    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_categories, container, false)

        setupRecyclerView()

        return rootView
    }

    private fun setupRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recycler_view_category)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = CategoriesAdapter(
            categories = mutableListOf(
                Category(nom = "toto"),
                Category(),
                Category(),
                Category(),
                Category()
            ),
            onCategoryChecked = { }
        )

        recyclerView.adapter = adapter
    }

}