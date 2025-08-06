package fr.arthur.pakko.utils

import androidx.room.Room
import fr.arthur.pakko.repositories.CategoryRepository
import fr.arthur.pakko.room.AppDatabase
import fr.arthur.pakko.usecase.CategoryUseCase
import fr.arthur.pakko.utils.AppConstants.DB_NAME
import fr.arthur.pakko.viewmodel.CategoryViewModel
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

    factory { CategoryUseCase(get()) }

    viewModel { CategoryViewModel(get()) }

}