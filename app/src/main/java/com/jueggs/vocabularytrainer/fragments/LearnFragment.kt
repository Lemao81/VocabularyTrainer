package com.jueggs.vocabularytrainer.fragments

import androidx.lifecycle.LifecycleOwner
import com.jueggs.andutils.base.BaseNavigationFragment
import com.jueggs.andutils.extension.*
import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.domain.models.StatsData
import com.jueggs.jutils.INVALIDL
import com.jueggs.vocabularytrainer.BR
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.domainservices.interfaces.IAnimationService
import com.jueggs.vocabularytrainer.models.FlashCardFlipAnimationData
import com.jueggs.vocabularytrainer.viewmodels.LearnViewModel
import kotlinx.android.synthetic.main.fragment_learn.*
import kotlinx.android.synthetic.main.include_card_flashcard.*
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
                showConfirmDialog(
                    R.string.dialog_remove_flashcard_title,
                    R.string.dialog_remove_flashcard_message,
                    viewModel::removeFlashCard,
                    viewModel::cancelFlashCardRemoval
                )
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
            postStats(FlashCardBox.ONE, stats1)
            postStats(FlashCardBox.TWO, stats2)
            postStats(FlashCardBox.THREE, stats3)
            postStats(FlashCardBox.FOUR, stats4)
            postStats(FlashCardBox.FIVE, stats5)
            postStats(FlashCardBox.SIX, stats6)
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

    override fun onStandby() = hideKeyboard()

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

    private fun postStats(box: FlashCardBox, data: StatsData) {
        viewModel.stats[box.index].apply {
            count.postValue(data.count.toString())
            isBold.postValue(data.isHasDueCards)
        }
    }
}