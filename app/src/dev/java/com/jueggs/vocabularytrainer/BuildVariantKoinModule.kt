package com.jueggs.vocabularytrainer

import com.jueggs.domain.services.DevFlashCardBoxService
import com.jueggs.domain.services.interfaces.IAppInitializer
import com.jueggs.domain.services.interfaces.IFlashCardBoxService
import com.jueggs.vocabularytrainer.domainservices.DevAlarmService
import com.jueggs.vocabularytrainer.domainservices.DevAppInitializer
import com.jueggs.vocabularytrainer.domainservices.interfaces.IAlarmService
import org.koin.dsl.module

val buildVariantKoinModule = module {
    single { DevFlashCardBoxService() as IFlashCardBoxService }
    single { DevAlarmService() as IAlarmService }
    single { DevAppInitializer(get()) as IAppInitializer }
}