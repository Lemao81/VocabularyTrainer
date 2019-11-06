package com.jueggs.vocabularytrainer

import com.jueggs.andutils.base.BaseApplication
import com.jueggs.domain.services.interfaces.IAppInitializer
import com.jueggs.jutils.logging.LogCategory
import com.jueggs.jutils.logging.Logger
import com.jueggs.vocabularytrainer.domainservices.interfaces.IAlarmService
import com.jueggs.vocabularytrainer.helper.DailyLearnNotification
import kotlinx.serialization.ImplicitReflectionSerializer
import org.koin.android.ext.android.inject

class App : BaseApplication(isDebug = BuildConfig.DEBUG) {
    val alarmService by inject<IAlarmService>()
    val appInitializer by inject<IAppInitializer>()

    @ImplicitReflectionSerializer
    override fun koinModules() = listOf(mainKoinModule, buildVariantKoinModule)

    override fun initialize() {
        DailyLearnNotification.createChannelIfNeeded(this)
        alarmService.scheduleDailyLearnAlarm(this)
        appInitializer.initialize()
    }

    override fun onUncaughtException(exception: Throwable) {
        Logger.newEntry().withCategory(LogCategory.UNHANDLEDEXCEPTION).withException(exception).logFatal()
    }

    companion object {
        val isDebug = BuildConfig.DEBUG
        val isRelease = !BuildConfig.DEBUG
        val isDev = BuildConfig.FLAVOR == "dev"
        val isProd = BuildConfig.FLAVOR == "prod"
    }
}