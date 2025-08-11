package fr.arthur.pakko.views.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.arthur.pakko.R
import fr.arthur.pakko.models.Element

class AddToModificationBottomSheet : BottomSheetDialogFragment() {
    lateinit var element: Element
    private lateinit var buttonEditElement: LinearLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.popup_open_to_modify, container, false)
        initComponent(view)
        return view
    }

    private fun initComponent(view: View) {
        buttonEditElement = view.findViewById(R.id.button_edit_element)
        buttonEditElement.setOnClickListener {
            // Envoie de l'élément à la modification
            findNavController().navigate(
                R.id.addElementFragment,
                Bundle().apply { putSerializable("element", element) }
            )
//            (activity as? MainActivity)?.highlightMenuItem(R.id.addElementFragment)
            dismiss()
        }
    }

}