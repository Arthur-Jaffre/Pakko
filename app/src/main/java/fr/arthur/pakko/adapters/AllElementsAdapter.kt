package fr.arthur.pakko.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.arthur.pakko.R
import fr.arthur.pakko.models.Element

class AllElementsAdapter(
    private val elements: MutableList<Element>,
    private val onElementClick: (Element) -> Unit
) : RecyclerView.Adapter<AllElementsAdapter.AllElementsViewHolder>() {

    class AllElementsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.item_title)
        val itemButton: ImageButton = itemView.findViewById(R.id.item_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllElementsViewHolder {
        return AllElementsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_element_1, parent, false)
        )
    }

    override fun getItemCount(): Int = elements.size

    override fun onBindViewHolder(holder: AllElementsViewHolder, position: Int) {
        val element = elements[position]
        holder.itemTitle.text = element.nom

        holder.itemButton.setOnClickListener {
            onElementClick(element)
        }
    }
}