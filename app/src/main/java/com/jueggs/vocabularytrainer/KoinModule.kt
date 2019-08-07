package com.jueggs.vocabularytrainer

import com.jueggs.common.services.FlashCardBoxService
import com.jueggs.database.AppDatabase
import com.jueggs.vocabularytrainer.usecases.CheckSomethingToLearnUseCase
import com.jueggs.vocabularytrainer.usecases.ShowNextFlashCardUseCase
import com.jueggs.vocabularytrainer.viewmodels.AddFlashCardViewModel
import com.jueggs.vocabularytrainer.viewmodels.LearnViewModel
import com.jueggs.vocabularytrainer.viewmodels.NothingToLearnViewModel
import com.jueggs.vocabularytrainer.viewmodels.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModule = module {
    viewModel { AddFlashCardViewModel() }
    viewModel { LearnViewModel(get()) }
    viewModel { NothingToLearnViewModel() }
    viewModel { SplashScreenViewModel(get()) }

    single { FlashCardBoxService() }
    single { CheckSomethingToLearnUseCase(get(), get()) }
    single { ShowNextFlashCardUseCase(get(), get()) }
    single { AppDatabase.getInstance(get()).getFlashCardStorageLayer() }
}