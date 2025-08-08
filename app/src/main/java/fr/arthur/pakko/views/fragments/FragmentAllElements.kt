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
import fr.arthur.pakko.viewmodel.ElementViewModel
import fr.arthur.pakko.views.bottomSheet.AddToCategoriesBottomSheet
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FragmentAllElements : Fragment() {

    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AllElementsAdapter
    private val elementViewModel: ElementViewModel by activityViewModel()

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
            onElementClick = { element ->
                openChoiceCategoriesBottomSheet(element)
            }
        )

        elementViewModel.elements.observe(viewLifecycleOwner) {
            adapter.submitList(it.toList())
        }
        elementViewModel.getAllElements()

        recyclerView.adapter = adapter
    }

    private fun openChoiceCategoriesBottomSheet(element: Element) {
        val bottomSheet = AddToCategoriesBottomSheet()
        bottomSheet.element = element
        bottomSheet.show(parentFragmentManager, "AddToCategories")
    }
}