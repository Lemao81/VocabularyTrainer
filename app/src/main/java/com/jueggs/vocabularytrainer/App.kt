package com.jueggs.vocabularytrainer

import com.jueggs.andutils.base.BaseApplication
import kotlinx.serialization.ImplicitReflectionSerializer
import org.koin.core.module.Module

class App : BaseApplication(isDebug = BuildConfig.DEBUG) {
    @ImplicitReflectionSerializer
    override fun koinModule(): Module? = koinModule
}