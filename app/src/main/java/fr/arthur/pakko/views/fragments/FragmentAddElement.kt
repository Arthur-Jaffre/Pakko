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
import fr.arthur.pakko.models.Element
import fr.arthur.pakko.viewmodel.CategoryViewModel
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_add_element, container, false)

        setupComponents()
        setupRecyclerView()

        return rootView
    }

    private fun setupComponents() {
        currentElement = arguments?.getSerializable("element") as? Element
        editText = rootView.findViewById(R.id.edit_element_input)
        editText.setText(currentElement?.nom ?: "")

        buttonDelete = rootView.findViewById(R.id.button_delete)
        if (currentElement != null) {
            buttonDelete.visibility = View.VISIBLE
            buttonDelete.setOnClickListener {
                // TODO : supprimer l'élément
            }
        }

        buttonSave = rootView.findViewById(R.id.button_save)
    }

    private fun setupRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recycler_view_all_category)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = AllCategoriesAdapter()
        categoryViewModel.categories.observe(viewLifecycleOwner) {
            adapter.submitList(it.toList())
        }
        categoryViewModel.getAllCategories()

        recyclerView.adapter = adapter
    }
}