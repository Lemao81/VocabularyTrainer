package com.jueggs.vocabularytrainer

import com.jueggs.domain.services.DevFlashCardBoxService
import com.jueggs.domain.services.interfaces.IFlashCardBoxService
import com.jueggs.vocabularytrainer.services.DevAlarmService
import com.jueggs.vocabularytrainer.services.interfaces.IAlarmService
import org.koin.dsl.module

val buildVariantKoinModule = module {
    single { DevFlashCardBoxService() as IFlashCardBoxService }
    single { DevAlarmService() as IAlarmService }
}