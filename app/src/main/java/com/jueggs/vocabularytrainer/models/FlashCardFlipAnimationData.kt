package com.jueggs.vocabularytrainer.models

import android.view.View

data class FlashCardFlipAnimationData(
    val flashCardView: View,
    val frontSideView: View,
    val backSideView: View,
    val endAction: Runnable
)