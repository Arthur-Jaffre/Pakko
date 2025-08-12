package fr.arthur.pakko.utils

import androidx.room.Room
import fr.arthur.pakko.repositories.CategoryRepository
import fr.arthur.pakko.repositories.DataRepository
import fr.arthur.pakko.repositories.ElementCategoryRepository
import fr.arthur.pakko.repositories.ElementRepository
import fr.arthur.pakko.room.AppDatabase
import fr.arthur.pakko.usecase.CategoryUseCase
import fr.arthur.pakko.usecase.ElementCategoryUseCase
import fr.arthur.pakko.usecase.ElementUseCase
import fr.arthur.pakko.usecase.ImportExportUseCase
import fr.arthur.pakko.utils.AppConstants.DB_NAME
import fr.arthur.pakko.viewmodel.CategoryViewModel
import fr.arthur.pakko.viewmodel.ElementViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            DB_NAME
        )
//            .fallbackToDestructiveMigration(true)
            .build()
    }

    single { get<AppDatabase>().categorieDao() }
    single { get<AppDatabase>().elementDao() }
    single { get<AppDatabase>().elementCategorieDao() }

    single { CategoryRepository(get()) }
    single { ElementRepository(get()) }
    single { ElementCategoryRepository(get()) }
    single { DataRepository(get(), get(), get()) }

    factory { CategoryUseCase(get()) }
    factory { ElementUseCase(get()) }
    factory { ElementCategoryUseCase(get()) }
    factory { ImportExportUseCase(get()) }

    viewModel { CategoryViewModel(get(), get()) }
    viewModel { ElementViewModel(get(), get()) }

}