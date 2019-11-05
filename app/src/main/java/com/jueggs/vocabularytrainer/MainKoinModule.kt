package com.jueggs.vocabularytrainer

import com.jueggs.andutils.logging.AndroidLogManager
import com.jueggs.andutils.logging.LogcatLogTarget
import com.jueggs.database.AppDatabase
import com.jueggs.database.mapper.FlashCardMapper
import com.jueggs.database.mapper.interfaces.IFlashCardMapper
import com.jueggs.database.repositories.FlashCardRepository
import com.jueggs.domain.models.FlashCardInputData
import com.jueggs.domain.models.FlashCardInputValidationResult
import com.jueggs.domain.services.FlashCardService
import com.jueggs.domain.services.StatsService
import com.jueggs.domain.services.interfaces.IFlashCardRepository
import com.jueggs.domain.services.interfaces.IFlashCardService
import com.jueggs.domain.services.interfaces.IStatsService
import com.jueggs.domain.usecases.AddFlashCardUseCase
import com.jueggs.domain.usecases.CheckSomethingToLearnUseCase
import com.jueggs.domain.usecases.DismissCorrectFlashCardUseCase
import com.jueggs.domain.usecases.DismissWrongFlashCardUseCase
import com.jueggs.domain.usecases.LoadFlashCardForEditingUseCase
import com.jueggs.domain.usecases.RemoveFlashCardUseCase
import com.jueggs.domain.usecases.ShowNextFlashCardUseCase
import com.jueggs.domain.usecases.UpdateFlashCardUseCase
import com.jueggs.domain.usecases.UpdateNothingToLearnViewStatsUseCase
import com.jueggs.domain.validators.FlashCardInputValidator
import com.jueggs.firebaseutils.logging.FirestoreLogTarget
import com.jueggs.jutils.logging.ILogManager
import com.jueggs.jutils.service.ISerializer
import com.jueggs.jutils.service.JsonSerializer
import com.jueggs.jutils.validation.IValidator
import com.jueggs.vocabularytrainer.domainservices.AnimationService
import com.jueggs.vocabularytrainer.domainservices.interfaces.IAnimationService
import com.jueggs.vocabularytrainer.viewmodels.AddFlashCardViewModel
import com.jueggs.vocabularytrainer.viewmodels.EditFlashCardViewModel
import com.jueggs.vocabularytrainer.viewmodels.LearnViewModel
import com.jueggs.vocabularytrainer.viewmodels.NothingToLearnViewModel
import com.jueggs.vocabularytrainer.viewmodels.SplashScreenViewModel
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ImplicitReflectionSerializer
val mainKoinModule = module {
    viewModel { AddFlashCardViewModel(get()) }
    viewModel { EditFlashCardViewModel(get(), get()) }
    viewModel { LearnViewModel(get(), get(), get(), get(), get()) }
    viewModel { NothingToLearnViewModel(get()) }
    viewModel { SplashScreenViewModel(get()) }

    single { FlashCardInputValidator() as IValidator<FlashCardInputData, FlashCardInputValidationResult> }
    single { FlashCardRepository(AppDatabase.getInstance(get()).getFlashCardDao(), get()) as IFlashCardRepository }
    single { JsonSerializer(Json(JsonConfiguration.Default)) as ISerializer }
    single { FlashCardMapper(get()) as IFlashCardMapper }
    single {
        AndroidLogManager(
            listOf(LogcatLogTarget(), FirestoreLogTarget(get())),
            BuildConfig.DEBUG,
            BuildConfig.FLAVOR
        ) as ILogManager
    }
    single { AnimationService(get()) as IAnimationService }
    single { FlashCardService(get()) as IFlashCardService }
    single { StatsService(get()) as IStatsService }

    single { AddFlashCardUseCase(get(), get()) }
    single { DismissCorrectFlashCardUseCase(get(), get()) }
    single { DismissWrongFlashCardUseCase(get()) }
    single { RemoveFlashCardUseCase(get()) }
    single { ShowNextFlashCardUseCase(get(), get(), get()) }
    single { UpdateNothingToLearnViewStatsUseCase(get(), get()) }
    single { CheckSomethingToLearnUseCase(get(), get()) }
    single { LoadFlashCardForEditingUseCase(get()) }
    single { UpdateFlashCardUseCase(get(), get()) }
}