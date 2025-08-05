package fr.arthur.pakko.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.arthur.pakko.R
import fr.arthur.pakko.adapters.ElementsByCategoriesAdapter
import fr.arthur.pakko.models.Category
import fr.arthur.pakko.models.Element

class FragmentElementsByCategories : Fragment() {
    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ElementsByCategoriesAdapter
    private lateinit var returnButton: ImageButton
    private lateinit var pageTitle: TextView
    private lateinit var category: Category

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_elements_by_category, container, false)

        setupComponents(rootView)
        setupRecyclerView()

        return rootView
    }


    private fun setupComponents(rootView: View) {
        category = requireArguments().getSerializable("category") as Category
        returnButton = rootView.findViewById(R.id.return_button)
        pageTitle = rootView.findViewById(R.id.title_element)
        pageTitle.text = category.nom
        returnButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recycler_view_category)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = ElementsByCategoriesAdapter(
            elements = mutableListOf(
                Element(nom = "element1"),
                Element(nom = "element2"),
                Element(nom = "element3"),
                Element(nom = "element4"),
                Element(nom = "element5")
            )
        )

        recyclerView.adapter = adapter
    }
}