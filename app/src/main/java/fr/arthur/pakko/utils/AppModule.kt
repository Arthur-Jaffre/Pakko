package fr.arthur.pakko.utils

import androidx.room.Room
import fr.arthur.pakko.repositories.CategoryRepository
import fr.arthur.pakko.repositories.ElementRepository
import fr.arthur.pakko.room.AppDatabase
import fr.arthur.pakko.usecase.CategoryUseCase
import fr.arthur.pakko.usecase.ElementUseCase
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
    single { get<AppDatabase>().elementCategorieDao() }
    single { get<AppDatabase>().elementDao() }

    single { CategoryRepository(get()) }
    single { ElementRepository(get()) }

    factory { CategoryUseCase(get()) }
    factory { ElementUseCase(get()) }

    viewModel { CategoryViewModel(get()) }
    viewModel { ElementViewModel(get()) }

}