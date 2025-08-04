package fr.arthur.pakko.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.arthur.pakko.R
import fr.arthur.pakko.adapters.AllCategoriesAdapter
import fr.arthur.pakko.models.Category

class FragmentAddElement : Fragment() {
    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var editText: EditText
    private lateinit var buttonDelete: Button
    private lateinit var buttonSave: Button
    private lateinit var adapter: AllCategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_add_element, container, false)

        setupComponents()
        setupRecyclerView()

        return rootView
    }

    private fun setupComponents() {
        editText = rootView.findViewById(R.id.edit_element_input)
        buttonDelete = rootView.findViewById(R.id.button_delete)
        buttonSave = rootView.findViewById(R.id.button_save)
    }

    private fun setupRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recycler_view_all_category)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = AllCategoriesAdapter(
            elements = mutableListOf(
                Category(nom = "categorie1"),
                Category(nom = "categorie2"),
                Category(nom = "categorie3"),
                Category(nom = "categorie4"),
                Category(nom = "categorie5")
            )
        )

        recyclerView.adapter = adapter
    }
}