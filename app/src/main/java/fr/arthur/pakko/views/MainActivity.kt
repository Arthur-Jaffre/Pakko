package fr.arthur.pakko.views

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.arthur.pakko.R
import fr.arthur.pakko.utils.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var pageTitle: TextView
    private lateinit var navigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        startDependencyInjection()
        setupViews()
        setupNavigation()
        observeNavigationChanges()
    }

    private fun startDependencyInjection() {
        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }
    }

    private fun setupViews() {
        pageTitle = findViewById(R.id.page_title)
        navigationView = findViewById(R.id.navigation_view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setupNavigation() {
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

            if (handled) {
                navController.navigate(item.itemId)
            }

            handled
        }
    }

    fun highlightMenuItem(itemId: Int) {
        navigationView.menu.findItem(itemId)?.isChecked = true
    }

    private fun observeNavigationChanges() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            pageTitle.text = destination.label
        }
    }
}
