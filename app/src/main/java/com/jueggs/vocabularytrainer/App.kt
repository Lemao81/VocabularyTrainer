package com.jueggs.vocabularytrainer

import com.jueggs.andutils.base.BaseApplication
import kotlinx.serialization.ImplicitReflectionSerializer

class App : BaseApplication(isDebug = BuildConfig.DEBUG) {
    @ImplicitReflectionSerializer
    override fun koinModules() = listOf(mainKoinModule, buildVariantKoinModule)
}