package com.jueggs.vocabularytrainer.domainservices

import android.content.Context
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.annotation.IntegerRes
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
        val animationDuration = context.getLong(R.integer.card_flip_duration)
        val halfAnimationDuration = animationDuration / 2

        data.flashCardView.animate().apply {
            rotationX(180f)
            duration = animationDuration
            interpolator = AccelerateDecelerateInterpolator()
            decelerateIfDev()
            start()
        }
        data.flashCardView.animate().apply {
            translationY(data.flashCardView.height / 4f)
            translationZ(data.flashCardView.height.toFloat() * 1.1f)
            duration = halfAnimationDuration
            interpolator = AccelerateDecelerateInterpolator()
            decelerateIfDev()
            withEndAction {
                data.frontSideView.alpha = 0f
                data.backSideView.alpha = 1f
                data.flashCardView.animate().apply {
                    translationY(0f)
                    translationZ(0f)
                    duration = halfAnimationDuration
                    interpolator = AccelerateDecelerateInterpolator()
                    decelerateIfDev()
                    withEndAction(data.endAction)
                    start()
                }
            }
            start()
        }
    }

    override fun animateDismissFlashCardCorrect(flashCardView: View, endAction: Runnable) = animateDismissFlashCard(flashCardView, endAction, DIRECTION_RIGHT)

    override fun animateDismissFlashCardWrong(flashCardView: View, endAction: Runnable) = animateDismissFlashCard(flashCardView, endAction, DIRECTION_LEFT)

    private fun animateDismissFlashCard(flashCardView: View, endAction: Runnable, directionScalar: Int) {
        flashCardView.animate().apply {
            translationX(directionScalar * Charles.screenWidth.toFloat())
            translationY(-flashCardView.height.toFloat())
            rotation(-directionScalar * context.getFloat(135))
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

// TODO lib
fun Context.getInteger(@IntegerRes resId: Int) = resources.getInteger(resId)

fun Context.getLong(@IntegerRes resId: Int) = (resources.getInteger(resId)).toLong()

fun Context.getFloat(@IntegerRes resId: Int) = (resources.getInteger(resId)).toFloat()