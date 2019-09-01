package com.jueggs.vocabularytrainer

import com.jueggs.andutils.usecase.IValidator
import com.jueggs.common.interfaces.ISerializer
import com.jueggs.common.services.FlashCardBoxService
import com.jueggs.common.services.JsonSerializer
import com.jueggs.database.AppDatabase
import com.jueggs.database.repositories.FlashCardRepository
import com.jueggs.common.interfaces.IFlashCardRepository
import com.jueggs.database.mapper.FlashCardMapper
import com.jueggs.database.mapper.interfaces.IFlashCardMapper
import com.jueggs.vocabularytrainer.models.FlashCardInputData
import com.jueggs.vocabularytrainer.usecases.*
import com.jueggs.vocabularytrainer.validators.FlashCardInputValidator
import com.jueggs.vocabularytrainer.viewmodels.AddFlashCardViewModel
import com.jueggs.vocabularytrainer.viewmodels.LearnViewModel
import com.jueggs.vocabularytrainer.viewmodels.NothingToLearnViewModel
import com.jueggs.vocabularytrainer.viewmodels.SplashScreenViewModel
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ImplicitReflectionSerializer
val koinModule = module {
    viewModel { AddFlashCardViewModel(get(), get(), get()) }
    viewModel { LearnViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { NothingToLearnViewModel(get()) }
    viewModel { SplashScreenViewModel(get()) }

    single { FlashCardBoxService() }
    single { FlashCardInputValidator() as IValidator<FlashCardInputData> }
    single { FlashCardRepository(AppDatabase.getInstance(get()).getFlashCardDao(), get()) as IFlashCardRepository }
    single { JsonSerializer(Json(JsonConfiguration.Stable)) as ISerializer }
    single { FlashCardMapper(get()) as IFlashCardMapper }

    single { AddFlashCardUseCase(get(), get()) }
    single { DismissCorrectFlashCardUseCase(get(), get()) }
    single { DismissWrongFlashCardUseCase(get()) }
    single { RemoveFlashCardUseCase(get()) }
    single { ShowNextFlashCardUseCase(get(), get()) }
    single { UpdateLearnViewStatsUseCase(get()) }
    single { UpdateNothingToLearnViewStatsUseCase(get()) }
    single { CheckSomethingToLearnUseCase(get(), get()) }
    single { LoadFlashCardForEditingUseCase(get()) }
    single { UpdateFlashCardUseCase(get(), get()) }
}