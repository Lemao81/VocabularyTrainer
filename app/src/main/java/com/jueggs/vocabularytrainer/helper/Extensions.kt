package com.jueggs.vocabularytrainer.helper

import android.animation.ValueAnimator
import android.view.ViewPropertyAnimator
import com.jueggs.vocabularytrainer.App
import com.jueggs.vocabularytrainer.BuildConfig

fun ViewPropertyAnimator.decelerateIfDev() = if (App.isDev) also { duration *= BuildConfig.ANIMATION_DECELERATION_FACTOR } else this

fun ValueAnimator.decelerateIfDev() = if (App.isDev) also { duration *= BuildConfig.ANIMATION_DECELERATION_FACTOR } else this