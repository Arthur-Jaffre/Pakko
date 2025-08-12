package fr.arthur.pakko.views

import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.arthur.pakko.R
import fr.arthur.pakko.usecase.ImportExportUseCase
import fr.arthur.pakko.utils.appModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.java.KoinJavaComponent.inject

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var pageTitle: TextView
    private lateinit var menuButton: ImageButton
    private lateinit var navigationView: BottomNavigationView
    private val useCase: ImportExportUseCase by inject(ImportExportUseCase::class.java)

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

    private fun setupMenu() {
        menuButton.setOnClickListener { view ->
            val popup = PopupMenu(view.context, view)
            popup.menuInflater.inflate(R.menu.file_menu, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_import -> {
                        performImport()
                        true
                    }

                    R.id.action_export -> {
                        performExport()
                        true
                    }

                    else -> false
                }
            }
            popup.show()
        }
    }


    private val exportJsonLauncher =
        registerForActivityResult(ActivityResultContracts.CreateDocument("application/json")) { uri: Uri? ->
            uri?.let {
                lifecycleScope.launch {
                    val jsonString = useCase.exportToJson()
                    writeTextToUri(it, jsonString)
                    Toast.makeText(this@MainActivity, "Export réussi", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private val importJsonLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                lifecycleScope.launch {
                    val jsonString = readTextFromUri(it)
                    useCase.importFromJson(jsonString)
                    Toast.makeText(this@MainActivity, "Import réussi", Toast.LENGTH_SHORT).show()
                }
            }
        }

    fun openJsonFilePicker() {
        importJsonLauncher.launch(arrayOf("application/json"))
    }

    // Fonction utilitaire pour lire le contenu texte d’un Uri
    private suspend fun readTextFromUri(uri: Uri): String = withContext(Dispatchers.IO) {
        contentResolver.openInputStream(uri)?.bufferedReader().use { it?.readText() } ?: ""
    }


    fun createJsonFile() {
        val filename = "export_${System.currentTimeMillis()}.json"
        exportJsonLauncher.launch(filename)
    }

    // Fonction utilitaire pour écrire du texte dans un Uri
    private suspend fun writeTextToUri(uri: Uri, text: String) = withContext(Dispatchers.IO) {
        contentResolver.openOutputStream(uri)?.bufferedWriter().use { it?.write(text) }
    }


    private fun performImport() {
        openJsonFilePicker()
    }

    private fun performExport() {
        createJsonFile()
    }

    private fun setupViews() {
        pageTitle = findViewById(R.id.page_title)
        menuButton = findViewById(R.id.main_menu)
        setupMenu()
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
