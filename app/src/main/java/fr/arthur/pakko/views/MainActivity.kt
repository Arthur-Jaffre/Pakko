package fr.arthur.pakko.views

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.arthur.pakko.R
import fr.arthur.pakko.utils.appModule
import fr.arthur.pakko.utils.replaceFragment
import fr.arthur.pakko.views.fragments.FragmentCategories
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        startKoin {
            modules(appModule)
        }

        setupNavigation()
        openCategoriesFragment()
    }

    private fun loadFragment(fragment: Fragment, titleRes: Int) {
        findViewById<TextView>(R.id.page_title).text = getString(titleRes)
        this.replaceFragment(
            fragment,
            R.id.fragment_container
        )
    }

    private fun openCategoriesFragment() {
        loadFragment(
            FragmentCategories(),
            R.string.categories_list
        )
    }

    private fun setupNavigation() {
        findViewById<BottomNavigationView>(R.id.navigation_view).setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_category -> {
                    openCategoriesFragment()
                    true
                }

                R.id.menu_element_list -> {
//                    loadFragment(
//                        AddIngredientFragment(),
//                        R.string.element_list
//                    )
                    true
                }

                R.id.menu_add_element -> {
//                    loadFragment(
//                        CollectionFragment(),
//                        R.string.add_element
//                    )
                    true
                }

                else -> false
            }
        }
    }
}