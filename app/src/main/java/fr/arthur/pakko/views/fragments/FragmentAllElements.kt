package fr.arthur.pakko.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.arthur.pakko.R
import fr.arthur.pakko.adapters.AllElementsAdapter
import fr.arthur.pakko.models.Element
import fr.arthur.pakko.views.bottomSheet.AddToCategoriesBottomSheet

class FragmentAllElements : Fragment() {

    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AllElementsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_all_elements, container, false)

        setupRecyclerView()

        return rootView
    }

    private fun setupRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recycler_view_all_elements)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = AllElementsAdapter(
            elements = mutableListOf(
                Element(nom = "element1"),
                Element(nom = "element2"),
                Element(nom = "element3"),
                Element(nom = "element4"),
                Element(nom = "element5")
            ),
            onElementClick = { element ->
                openChoiceCategoriesBottomSheet(element)
            }
        )

        recyclerView.adapter = adapter
    }

    private fun openChoiceCategoriesBottomSheet(element: Element) {
        val bottomSheet = AddToCategoriesBottomSheet()
        bottomSheet.element = element
        bottomSheet.show(parentFragmentManager, "AddToCategories")
    }
}