package com.jueggs.vocabularytrainer

import com.jueggs.common.services.FlashCardBoxService
import com.jueggs.database.AppDatabase
import com.jueggs.database.repositories.FlashCardRepositoryImpl
import com.jueggs.database.repositories.interfaces.FlashCardRepository
import com.jueggs.vocabularytrainer.validators.AddFlashCardInputValidator
import com.jueggs.vocabularytrainer.viewmodels.AddFlashCardViewModel
import com.jueggs.vocabularytrainer.viewmodels.LearnViewModel
import com.jueggs.vocabularytrainer.viewmodels.NothingToLearnViewModel
import com.jueggs.vocabularytrainer.viewmodels.SplashScreenViewModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModule = module {
    viewModel { AddFlashCardViewModel(get(), get(), get()) }
    viewModel { LearnViewModel(get(), get(), get(), get()) }
    viewModel { NothingToLearnViewModel(get()) }
    viewModel { SplashScreenViewModel(get(), get()) }

    single { FlashCardBoxService() }
    single { AddFlashCardInputValidator() }
    single { FlashCardRepositoryImpl(AppDatabase.getInstance(get()).getFlashCardDao()) as FlashCardRepository }
    single { Json(JsonConfiguration.Stable) }
}