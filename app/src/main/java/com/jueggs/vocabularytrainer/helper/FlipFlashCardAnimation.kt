package com.jueggs.vocabularytrainer.helper

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.core.animation.doOnEnd
import com.jueggs.andutils.adapter._addListener
import com.jueggs.jutils.extension.being
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.models.FlipFlashCardAnimationData

object FlipFlashCardAnimation {
    fun animate(data: FlipFlashCardAnimationData) {
        AnimatorInflater.loadAnimator(data.context, R.animator.flip_flashcard).apply {
            val translationZAnimator = being<AnimatorSet>().childAnimations[1].being<ObjectAnimator>()
            val translationYAnimator = being<AnimatorSet>().childAnimations[2].being<ObjectAnimator>()

            translationZAnimator._addListener {
                _onAnimationRepeat {
                    data.frontSideView.alpha = 0f
                    data.backSideView.alpha = 1f
                }
            }
            translationZAnimator.setFloatValues(0f, data.flashCardView.height.toFloat())
            translationYAnimator.setFloatValues(0f, data.flashCardView.height / 4f)
            doOnEnd { data.viewModel.setBackSideRevealed() }
            setTarget(data.flashCardView)
            start()
        }
    }
}