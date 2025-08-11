package fr.arthur.pakko.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.arthur.pakko.R
import fr.arthur.pakko.models.Element

class ElementsSelectAdapter(
    private val onElementClicked: (Element) -> Unit
) : RecyclerView.Adapter<ElementsSelectAdapter.ElementsSelectViewHolder>() {
    private val elements = mutableListOf<Element>()

    class ElementsSelectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.item_title)
        val radioButton: RadioButton = itemView.findViewById(R.id.category_radioButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementsSelectViewHolder {
        return ElementsSelectViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_element_select, parent, false)
        )
    }

    override fun getItemCount(): Int = elements.size

    override fun onBindViewHolder(holder: ElementsSelectViewHolder, position: Int) {
        val item = elements[position]
        holder.itemTitle.text = item.nom
        holder.radioButton.setOnClickListener {
            onElementClicked(item)
        }
    }

    fun submitList(newItems: List<Element>) {
        elements.clear()
        elements.addAll(newItems)
        notifyDataSetChanged()
    }
}