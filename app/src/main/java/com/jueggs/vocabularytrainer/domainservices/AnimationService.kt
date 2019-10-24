package com.jueggs.vocabularytrainer.domainservices

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import com.jueggs.andutils.extension.doOnHalfTime
import com.jueggs.andutils.extension.getLong
import com.jueggs.vocabularytrainer.Charles
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.domainservices.interfaces.IAnimationService
import com.jueggs.vocabularytrainer.helper.decelerateIfDev
import com.jueggs.vocabularytrainer.models.FlashCardFlipAnimationData

class AnimationService(
    private val context: Context
) : IAnimationService {
    override fun animateFlashCardDisplay(flashCardView: View) {
        flashCardView.animate().apply {
            alpha(1f)
            duration = 1000
            interpolator = DecelerateInterpolator()
            start()
        }
    }

    override fun animateFlashCardFlip(data: FlashCardFlipAnimationData) {
        val rotationValueHolder = PropertyValuesHolder.ofFloat(View.ROTATION_X, 180f)
        val translationYValueHolder = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0f, data.flashCardView.height / 4f, 0f)
        val translationZValueHolder = PropertyValuesHolder.ofFloat(View.TRANSLATION_Z, 0f, data.flashCardView.height.toFloat() * 1.1f, 0f)

        ObjectAnimator.ofPropertyValuesHolder(data.flashCardView, rotationValueHolder, translationYValueHolder, translationZValueHolder).apply {
            duration = context.getLong(R.integer.card_flip_duration)
            interpolator = AccelerateDecelerateInterpolator()
            decelerateIfDev()
            doOnHalfTime {
                data.frontSideView.alpha = 0f
                data.backSideView.alpha = 1f
            }
            doOnEnd { data.endAction.run() }
            start()
        }
    }

    override fun animateDismissFlashCardCorrect(flashCardView: View, endAction: Runnable) = animateDismissFlashCard(flashCardView, endAction, DIRECTION_RIGHT)

    override fun animateDismissFlashCardWrong(flashCardView: View, endAction: Runnable) = animateDismissFlashCard(flashCardView, endAction, DIRECTION_LEFT)

    private fun animateDismissFlashCard(flashCardView: View, endAction: Runnable, directionScalar: Int) {
        flashCardView.animate().apply {
            translationX(directionScalar * Charles.screenWidth.toFloat())
            translationY(-flashCardView.height.toFloat())
            rotation(-directionScalar * 135f)
            duration = context.getLong(R.integer.card_dismiss_duration)
            interpolator = AccelerateInterpolator()
            decelerateIfDev()
            withEndAction {
                flashCardView.apply {
                    alpha = 0f
                    rotation = 0f
                    translationX = 0f
                    translationY = 0f
                }
                endAction.run()
            }
            start()
        }
    }

    companion object {
        const val DIRECTION_LEFT = -1
        const val DIRECTION_RIGHT = 1
    }
}