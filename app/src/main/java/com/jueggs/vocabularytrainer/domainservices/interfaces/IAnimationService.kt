package com.jueggs.vocabularytrainer.domainservices.interfaces

import android.view.View
import com.jueggs.vocabularytrainer.models.FlashCardFlipAnimationData

interface IAnimationService {
    fun animateFlashCardFlip(data: FlashCardFlipAnimationData)
    fun animateDismissFlashCardCorrect(flashCardView: View, endAction: Runnable)
    fun animateDismissFlashCardWrong(flashCardView: View, endAction: Runnable)
}