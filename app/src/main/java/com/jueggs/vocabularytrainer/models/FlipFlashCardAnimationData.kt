package com.jueggs.vocabularytrainer.models

import android.content.Context
import android.view.View
import com.jueggs.vocabularytrainer.viewmodels.LearnViewModel

data class FlipFlashCardAnimationData(
    val context: Context,
    val viewModel: LearnViewModel,
    val flashCardView: View,
    val frontSideView: View,
    val backSideView: View
)