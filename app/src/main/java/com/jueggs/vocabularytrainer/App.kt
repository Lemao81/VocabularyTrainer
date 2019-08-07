package com.jueggs.vocabularytrainer

import com.jueggs.andutils.base.BaseApplication
import org.koin.core.module.Module

class App : BaseApplication(isDebug = BuildConfig.DEBUG) {
    override fun koinModule(): Module? = koinModule
}