package com.jueggs.vocabularytrainer.helper

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.animation.doOnEnd
import com.jueggs.jutils.extension.being
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewmodels.LearnViewModel

object FlipFlashCardAnimation {
    fun animate(rootView: View, viewModel: LearnViewModel) {
        AnimatorInflater.loadAnimator(rootView.context, R.animator.flip_flashcard).apply {
            val flashCardView = rootView.findViewById<CardView>(R.id.cardFlashCard)
            val frontSideView = rootView.findViewById<View>(R.id.frameFrontSide)
            val backSideView = rootView.findViewById<View>(R.id.frameBackSide)
            val set = this as AnimatorSet
            val flipAnimator = set.childAnimations[0].being<ObjectAnimator>()
            val translationZAnimator = set.childAnimations[1].being<ObjectAnimator>()
            val translationYAnimator = set.childAnimations[2].being<ObjectAnimator>()
            var isHalfWayThrough = false

            flipAnimator.addUpdateListener {
                if (!isHalfWayThrough && it.animatedFraction > 0.5) {
                    frontSideView.alpha = 0f
                    backSideView.alpha = 1f
                    isHalfWayThrough = true
                }
            }

            translationZAnimator.setFloatValues(0f, flashCardView.height.toFloat())
            translationYAnimator.setFloatValues(0f, flashCardView.height / 4f)
            doOnEnd { viewModel.setBackSideRevealed() }
            setTarget(flashCardView)
            start()
        }
    }
}