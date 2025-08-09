package fr.arthur.pakko.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.arthur.pakko.R
import fr.arthur.pakko.models.CategorieUi

class AllCategoriesAdapter(
    private val onCategoryChecked: ((CategorieUi) -> Unit)? = null
) : RecyclerView.Adapter<AllCategoriesAdapter.AllCategoriesViewHolder>() {
    private val categoriesUi = mutableListOf<CategorieUi>()

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

    override fun getItemCount(): Int = categoriesUi.size

    override fun onBindViewHolder(holder: AllCategoriesViewHolder, position: Int) {
        val item = categoriesUi[position]
        holder.itemTitle.text = item.category.nom
        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.isChecked = item.coche
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            item.coche = isChecked
            onCategoryChecked?.invoke(item)
        }
    }

    fun submitList(newItems: List<CategorieUi>) {
        categoriesUi.clear()
        categoriesUi.addAll(newItems)
        notifyDataSetChanged()
    }

    fun getSelectedCategories(): List<CategorieUi> {
        return categoriesUi.filter { it.coche }
    }
}