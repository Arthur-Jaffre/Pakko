package fr.arthur.pakko.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.arthur.pakko.R
import fr.arthur.pakko.models.ElementUi

class ElementsByCategoriesAdapter(
    private val onElementClick: (ElementUi) -> Unit
) : RecyclerView.Adapter<ElementsByCategoriesAdapter.ElementsByCategoriesViewHolder>() {

    private val elements = mutableListOf<ElementUi>()

    class ElementsByCategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.item_title)

        // TODO : gérer le checkbox
        val isChecked: CheckBox = itemView.findViewById(R.id.element_checkBox)
        val itemButton: ImageButton = itemView.findViewById(R.id.item_button)
        val itemComment: TextView = itemView.findViewById(R.id.item_comment)
    }

    fun submitList(newItems: List<ElementUi>) {
        elements.clear()
        elements.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ElementsByCategoriesViewHolder {
        return ElementsByCategoriesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_element_category, parent, false)
        )
    }

    override fun getItemCount(): Int = elements.size

    override fun onBindViewHolder(holder: ElementsByCategoriesViewHolder, position: Int) {
        val elementUi = elements[position]
        holder.itemTitle.text = elementUi.element.nom
        holder.itemComment.text = elementUi.comment
        holder.itemButton.setOnClickListener {
            onElementClick(elementUi)
        }

    }
}