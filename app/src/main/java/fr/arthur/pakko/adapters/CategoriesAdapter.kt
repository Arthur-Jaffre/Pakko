package fr.arthur.pakko.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.arthur.pakko.R
import fr.arthur.pakko.models.Category

class CategoriesAdapter(
    private val onCategoryClick: (Category) -> Unit,
    private val onCategoryModifyClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    private val categories = mutableListOf<Category>()

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.item_title)
        val itemSettings: ImageButton = itemView.findViewById(R.id.settings_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.itemTitle.text = category.nom
        // modifier
        holder.itemSettings.setOnClickListener {
            onCategoryModifyClick(category)
        }
        // ouvrir éléments par catégories
        holder.itemView.setOnClickListener {
            onCategoryClick(category)
        }
    }

    fun submitList(newItems: List<Category>) {
        categories.clear()
        categories.addAll(newItems)
        notifyDataSetChanged()
    }
}
