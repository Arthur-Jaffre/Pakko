package fr.arthur.pakko.views

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.arthur.pakko.R
import fr.arthur.pakko.utils.appModule
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : AppCompatActivity() {

    private lateinit var navController: androidx.navigation.NavController
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
        navigationView.setupWithNavController(navController)
    }

    private fun observeNavigationChanges() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            pageTitle.text = destination.label
        }
    }
}
