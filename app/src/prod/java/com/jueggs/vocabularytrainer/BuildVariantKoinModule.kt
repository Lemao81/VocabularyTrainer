package com.jueggs.vocabularytrainer

import com.jueggs.domain.interfaces.IFlashCardBoxService
import com.jueggs.domain.services.ProdFlashCardBoxService
import org.koin.dsl.module

val buildVariantKoinModule = module {
    single { ProdFlashCardBoxService() as IFlashCardBoxService }
}