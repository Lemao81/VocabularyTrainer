package com.jueggs.vocabularytrainer.helper

import android.view.ViewPropertyAnimator
import com.jueggs.vocabularytrainer.App
import com.jueggs.vocabularytrainer.BuildConfig

fun ViewPropertyAnimator.decelerateIfDev(): ViewPropertyAnimator {
    if (App.isDev) {
        duration *= BuildConfig.ANIMATION_DECELERATION_FACTOR
    }

    return this
}