package com.jueggs.vocabularytrainer

import com.jueggs.domain.services.ProdFlashCardBoxService
import com.jueggs.domain.services.interfaces.IFlashCardBoxService
import com.jueggs.vocabularytrainer.services.ProdAlarmService
import com.jueggs.vocabularytrainer.services.interfaces.IAlarmService
import org.koin.dsl.module

val buildVariantKoinModule = module {
    single { ProdFlashCardBoxService() as IFlashCardBoxService }
    single { ProdAlarmService() as IAlarmService }
}