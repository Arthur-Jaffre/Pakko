package fr.arthur.pakko.views

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.arthur.pakko.R
import fr.arthur.pakko.usecase.ImportExportUseCase
import fr.arthur.pakko.utils.ImportExportHandler
import fr.arthur.pakko.utils.appModule
import fr.arthur.pakko.views.navigation.MenuImportHandler
import fr.arthur.pakko.views.navigation.NavigationHandler
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.java.KoinJavaComponent.inject

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var pageTitle: TextView
    private lateinit var menuButton: ImageButton
    private lateinit var navigationView: BottomNavigationView
    private val useCase: ImportExportUseCase by inject(ImportExportUseCase::class.java)

    private lateinit var importExportHandler: ImportExportHandler
    private lateinit var menuImportHandler: MenuImportHandler
    private lateinit var navigationHandler: NavigationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        startDependencyInjection()
        setupViews()

        navigationHandler = NavigationHandler(navigationView, navController)

        setupNavigation()
        observeNavigationChanges()

        importExportHandler = ImportExportHandler(this, useCase)
        menuImportHandler = MenuImportHandler(menuButton, importExportHandler)
        menuImportHandler.setupMenu()
    }

    private fun startDependencyInjection() {
        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }
    }

    private fun setupViews() {
        pageTitle = findViewById(R.id.page_title)
        menuButton = findViewById(R.id.main_menu)
        navigationView = findViewById(R.id.navigation_view)
        navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                as NavHostFragment).navController
    }

    private fun setupNavigation() {
        navigationHandler.setup()
    }

    private fun observeNavigationChanges() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            pageTitle.text = destination.label
        }
    }
}
