package com.jueggs.vocabularytrainer.fragments

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.jueggs.andutils.base.BaseNavigationFragment
import com.jueggs.andutils.extension.colorResToInt
import com.jueggs.andutils.extension.fadeIn
import com.jueggs.andutils.extension.longToast
import com.jueggs.andutils.extension.shortToast
import com.jueggs.andutils.extension.showConfirmDialog
import com.jueggs.andutils.extension.visibleOrInvisible
import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.jutils.INVALIDL
import com.jueggs.vocabularytrainer.BR
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.domainservices.interfaces.IAnimationService
import com.jueggs.vocabularytrainer.models.FlashCardFlipAnimationData
import com.jueggs.vocabularytrainer.viewmodels.LearnViewModel
import kotlinx.android.synthetic.main.fragment_learn.*
import kotlinx.android.synthetic.main.include_card_flashcard.*
import org.jetbrains.anko.dip
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LearnFragment : BaseNavigationFragment() {
    val viewModel by viewModel<LearnViewModel>()
    val animationService by inject<IAnimationService>()

    override fun layout() = R.layout.fragment_learn
    override fun bindingItems() = mapOf(BR.viewModel to viewModel)

    override fun observeLiveData(owner: LifecycleOwner) {
        viewModel.viewStateStore.observe(owner) {
            if (isShouldNavigateToNothingToLearn) {
                navController.navigate(R.id.action_learnFragment_to_nothingToLearnFragment)
            }
            if (isShouldNavigateToAddFlashCard) {
                navController.navigate(R.id.action_learnFragment_to_addFlashCardFragment)
            }
            if (isShouldNavigateToEditFlashCard) {
                val navDirection = LearnFragmentDirections.actionLearnFragmentToEditFlashCardFragment(viewModel.currentFlashCardId ?: INVALIDL)
                navController.navigate(navDirection)
            }
            if (isShouldMessageCardRemoved) {
                shortToast(R.string.message_card_removed)
            }
            if (isShouldShowRemoveFlashCardConfirmation) {
                showConfirmDialog(R.string.dialog_remove_flashcard_title, R.string.dialog_remove_flashcard_message, viewModel::removeFlashCard, viewModel::cancelFlashCardRemoval)
            }
            if (!isRevealing) {
                cardFlashCard.rotationX = if (isRevealed) 180f else 0f
            }
            cardFlashCard.isClickable = !isRevealed
            frameFrontSide.alpha = if (isRevealed) 0f else 1f
            frameBackSide.alpha = if (isRevealed) 1f else 0f
            fabWrong.visibleOrInvisible = isRevealing || isRevealed
            fabCorrect.visibleOrInvisible = isRevealing || isRevealed
            longMessage?.let { longToast(it) }
            frontSideText?.let { viewModel.frontSideText.postValue(it) }
            backSideText?.let { viewModel.backSideText.postValue(it) }
            nextFlashCardBox?.let { box ->
                viewModel.boxNumber.postValue(box.number.toString())
                cardFlashCard.setCardBackgroundColor(mapFlashCardBoxToColorInt(box))
            }
            viewModel.currentFlashCardId = currentFlashCardId
            viewModel.stats[FlashCardBox.ONE.index].postValue(stats1.toString())
            viewModel.stats[FlashCardBox.TWO.index].postValue(stats2.toString())
            viewModel.stats[FlashCardBox.THREE.index].postValue(stats3.toString())
            viewModel.stats[FlashCardBox.FOUR.index].postValue(stats4.toString())
            viewModel.stats[FlashCardBox.FIVE.index].postValue(stats5.toString())
            viewModel.stats[FlashCardBox.SIX.index].postValue(stats6.toString())
            if (isShouldAnimateCardDisplay) {
                cardFlashCard.fadeIn()
            }
            if (isShouldAnimateCardFlip) {
                val data = FlashCardFlipAnimationData(cardFlashCard, frameFrontSide, frameBackSide, Runnable { viewModel.setBackSideRevealed() })
                animationService.animateFlashCardFlip(data)
            }
            if (isShouldAnimateDismissCorrect) {
                animationService.animateDismissFlashCardCorrect(cardFlashCard, Runnable { viewModel.showNextFlashCard() })
            }
            if (isShouldAnimateDismissWrong) {
                animationService.animateDismissFlashCardWrong(cardFlashCard, Runnable { viewModel.showNextFlashCard() })
            }
            fabMenu.close(true)
        }
    }

    override fun setListeners() {
        val cardFabElevationDiff = cardFlashCard.elevation - requireContext().dip2px(5)
        fabMenu.setOnMenuToggleListener { opened ->
            if (opened) {
                cardFlashCard.translationZ = -(cardFabElevationDiff + requireActivity().dip(2))
            } else {
                cardFlashCard.translationZ = 0f
            }
        }
    }

    private fun mapFlashCardBoxToColorInt(box: FlashCardBox): Int {
        val colorId = when (box) {
            FlashCardBox.TWO -> R.color.box2_background
            FlashCardBox.THREE -> R.color.box3_background
            FlashCardBox.FOUR -> R.color.box4_background
            FlashCardBox.FIVE -> R.color.box5_background
            FlashCardBox.SIX -> R.color.box6_background
            else -> R.color.box1_background
        }

        return requireContext().colorResToInt(colorId)
    }
}

fun Context.dip2px(dip: Int): Int = (dip * resources.displayMetrics.density).toInt()
fun Context.dip2px(dip: Float): Int = (dip * resources.displayMetrics.density).toInt()