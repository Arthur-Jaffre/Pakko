package fr.arthur.pakko.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.arthur.pakko.R
import fr.arthur.pakko.models.Category

class AllCategoriesAdapter(
    private val elements: MutableList<Category>,
) : RecyclerView.Adapter<AllCategoriesAdapter.AllCategoriesViewHolder>() {

    class AllCategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.item_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCategoriesViewHolder {
        return AllCategoriesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category_select, parent, false)
        )
    }

    override fun getItemCount(): Int = elements.size

    override fun onBindViewHolder(holder: AllCategoriesViewHolder, position: Int) {
        val element = elements[position]
        holder.itemTitle.text = element.nom
    }
}