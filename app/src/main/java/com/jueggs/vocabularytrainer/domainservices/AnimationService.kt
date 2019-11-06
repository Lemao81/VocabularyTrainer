package com.jueggs.vocabularytrainer.domainservices

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import androidx.core.animation.doOnEnd
import com.jueggs.andutils.AppManager
import com.jueggs.andutils.extension.getLong
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.domainservices.interfaces.IAnimationService
import com.jueggs.vocabularytrainer.models.FlashCardFlipAnimationData

class AnimationService(
    private val context: Context
) : IAnimationService {

    override fun animateFlashCardFlip(data: FlashCardFlipAnimationData) {
        val yAmplitude = data.flashCardView.height / 4f
        val zAmplitude = data.flashCardView.height * 1.1f
        val rotationHolder = PropertyValuesHolder.ofFloat(View.ROTATION_X, 180f)
        val yTranslationOutHolder = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0f, yAmplitude)
        val yTranslationInHolder = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, yAmplitude, 0f)
        val zTranslationOutHolder = PropertyValuesHolder.ofFloat(View.TRANSLATION_Z, 0f, zAmplitude)
        val zTranslationInHolder = PropertyValuesHolder.ofFloat(View.TRANSLATION_Z, zAmplitude, 0f)

        ObjectAnimator.ofPropertyValuesHolder(data.flashCardView, rotationHolder).apply {
            duration = context.getLong(R.integer.card_flip_duration)
            interpolator = AccelerateDecelerateInterpolator()
            doOnEnd { data.endAction.run() }
            start()
        }
        val translationOutAnimation = ObjectAnimator.ofPropertyValuesHolder(data.flashCardView, yTranslationOutHolder, zTranslationOutHolder).apply {
            duration = context.getLong(R.integer.card_flip_duration) / 2
            interpolator = AccelerateDecelerateInterpolator()
            doOnEnd {
                data.frontSideView.alpha = 0f
                data.backSideView.alpha = 1f
            }
        }
        val translationInAnimation = ObjectAnimator.ofPropertyValuesHolder(data.flashCardView, yTranslationInHolder, zTranslationInHolder).apply {
            duration = context.getLong(R.integer.card_flip_duration) / 2
            interpolator = AccelerateDecelerateInterpolator()
        }
        AnimatorSet().apply { play(translationOutAnimation).before(translationInAnimation) }.start()
    }

    override fun animateDismissFlashCardCorrect(flashCardView: View, endAction: Runnable) = animateDismissFlashCard(flashCardView, endAction, DIRECTION_RIGHT)

    override fun animateDismissFlashCardWrong(flashCardView: View, endAction: Runnable) = animateDismissFlashCard(flashCardView, endAction, DIRECTION_LEFT)

    private fun animateDismissFlashCard(flashCardView: View, endAction: Runnable, directionScalar: Int) {
        val rotationHolder = PropertyValuesHolder.ofFloat(View.ROTATION, -directionScalar * 135f)
        val xTranslationHolder = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, directionScalar * AppManager.screenWidth.toFloat())
        val yTranslationHolder = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -flashCardView.height.toFloat())
        val alphaHolder = PropertyValuesHolder.ofFloat(View.ALPHA, 0f)

        ObjectAnimator.ofPropertyValuesHolder(flashCardView, rotationHolder, xTranslationHolder, yTranslationHolder).apply {
            duration = context.getLong(R.integer.card_dismiss_duration)
            interpolator = AccelerateInterpolator()
            doOnEnd {
                flashCardView.apply {
                    rotation = 0f
                    translationX = 0f
                    translationY = 0f
                }
                endAction.run()
            }
            start()
        }
        ObjectAnimator.ofPropertyValuesHolder(flashCardView, alphaHolder).apply {
            startDelay = context.getLong(R.integer.card_dismiss_duration) / 2
            duration = context.getLong(R.integer.card_dismiss_duration)
            interpolator = AccelerateInterpolator()
            start()
        }
    }

    companion object {
        const val DIRECTION_LEFT = -1
        const val DIRECTION_RIGHT = 1
    }
}