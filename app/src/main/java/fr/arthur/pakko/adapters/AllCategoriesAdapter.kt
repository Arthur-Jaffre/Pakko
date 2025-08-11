package fr.arthur.pakko.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.arthur.pakko.R
import fr.arthur.pakko.models.Category
import fr.arthur.pakko.models.CategoryUi

class AllCategoriesAdapter(
) : RecyclerView.Adapter<AllCategoriesAdapter.AllCategoriesViewHolder>() {
    private val elementCategory = mutableListOf<CategoryUi>()

    class AllCategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.item_title)
        val checkBox: CheckBox = itemView.findViewById(R.id.category_checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCategoriesViewHolder {
        return AllCategoriesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category_select, parent, false)
        )
    }

    override fun getItemCount(): Int = elementCategory.size

    override fun onBindViewHolder(holder: AllCategoriesViewHolder, position: Int) {
        val item = elementCategory[position]
        holder.itemTitle.text = item.category.nom
        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.isChecked = item.coche
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            item.coche = isChecked
        }
    }

    fun submitList(newItems: List<CategoryUi>) {
        elementCategory.clear()
        elementCategory.addAll(newItems)
        notifyDataSetChanged()
    }

    fun getSelectedCategories(): List<Category> {
        return elementCategory.filter { it.coche }.map { it.category }
    }
}