package fr.arthur.pakko.views.navigation

import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.arthur.pakko.R

class NavigationHandler(
    private val navigationView: BottomNavigationView,
    private val navController: NavController
) {
    fun setup() {
        navigationView.setOnItemSelectedListener { item ->
            val handled = when (item.itemId) {
                R.id.nav_categories -> {
                    navController.popBackStack(R.id.categoriesFragment, false)
                    true
                }

                R.id.allElementsFragment -> {
                    navController.popBackStack(R.id.allElementsFragment, false)
                    true
                }

                R.id.addElementFragment -> {
                    navController.popBackStack(R.id.addElementFragment, false)
                    true
                }

                else -> false
            }
            if (handled) navController.navigate(item.itemId)
            handled
        }
    }
}
