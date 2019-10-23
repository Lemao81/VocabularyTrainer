package com.jueggs.vocabularytrainer

import android.content.Context
import android.util.DisplayMetrics
import com.jueggs.jutils.pairOf
import org.jetbrains.anko.windowManager
import org.koin.core.KoinComponent
import org.koin.core.inject

object Charles : KoinComponent {
    private val context by inject<Context>()

    val screenWidth = DisplayMetrics().apply { context.windowManager.defaultDisplay.getMetrics(this) }.widthPixels
    val screenHeight = DisplayMetrics().apply { context.windowManager.defaultDisplay.getMetrics(this) }.heightPixels
    val screenMetrics: Pair<Int, Int>
        get() {
            val metrics = DisplayMetrics().apply { context.windowManager.defaultDisplay.getMetrics(this) }

            return pairOf(metrics.widthPixels, metrics.heightPixels)
        }
}