package com.jueggs.vocabularytrainer

import com.jueggs.domain.interfaces.IFlashCardBoxService
import com.jueggs.domain.services.DevFlashCardBoxService
import org.koin.dsl.module

val buildVariantKoinModule = module {
    single { DevFlashCardBoxService() as IFlashCardBoxService }
}