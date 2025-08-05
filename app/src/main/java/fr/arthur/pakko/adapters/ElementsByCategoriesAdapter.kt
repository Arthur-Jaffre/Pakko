package fr.arthur.pakko.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.arthur.pakko.R
import fr.arthur.pakko.models.Element

class ElementsByCategoriesAdapter(
    private val elements: MutableList<Element>,
) : RecyclerView.Adapter<ElementsByCategoriesAdapter.ElementsByCategoriesViewHolder>() {

    class ElementsByCategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.item_title)
        val isChecked: CheckBox = itemView.findViewById(R.id.element_checkBox)
        val itemComment: TextView = itemView.findViewById(R.id.item_comment)
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
        val element = elements[position]
        holder.itemTitle.text = element.nom
    }
}